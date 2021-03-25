package com.axreng.backend.webcrawling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

import com.axreng.backend.exceptions.InputException;

public class CrawlingProcessor {

	private URL baseURL;
	private String keyword;
	private Integer maxResults;

	public CrawlingProcessor(String baseUrl, String keyword, Integer maxResults) throws InputException {
		this.baseURL = verifyURL(baseUrl);
		this.keyword = verifyKeyword(keyword);
		this.maxResults = verifyMaxResults(maxResults);
	}

	public void executeCrawling() throws IOException {
		
	}

	private URL verifyURL(String baseUrl) throws InputException {
		if (baseUrl == null) {
			throw new InputException("Required item: " + baseUrl);
		}

		try {
			URL url = new URL(baseUrl); // Do URL validation.
			url.toURI(); // Do additional URI validations.

			return url;
		} catch (MalformedURLException | URISyntaxException e) {
			throw new InputException(baseUrl, e.getCause());
		}
	}

	private String verifyKeyword(String keyword) throws InputException {
		if (keyword == null) {
			throw new InputException("Required item: " + keyword);
		}else if(!keyword.matches("^[a-zA-Z0-9_]*$")) {
			throw new InputException("Invalid keyword, should not contain non alphanumeric characters.");
		} else if (keyword.length() < 4 || keyword.length() > 32) {
			throw new InputException(keyword.length());
		}
		
		return keyword;
	}
	
	private Integer verifyMaxResults(Integer maxResults) {
		if(maxResults == null || maxResults == 0 || maxResults < -1){
			return -1;
		}
		
		return maxResults;
	}

	public URL getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(URL baseURL) {
		this.baseURL = baseURL;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}
	
}
