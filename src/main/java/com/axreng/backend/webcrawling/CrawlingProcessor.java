package com.axreng.backend.webcrawling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.axreng.backend.exceptions.InputException;

public class CrawlingProcessor {

	private URL baseURL;
	private String keyword;
	private Integer maxResults;
	private List<String> resultList;

	public CrawlingProcessor(String baseUrl, String keyword, Integer maxResults) throws InputException {
		this.baseURL = verifyURL(baseUrl);
		this.keyword = verifyKeyword(keyword);
		this.maxResults = verifyMaxResults(maxResults);
		
		this.resultList = new ArrayList<>();
	}

	public void executeCrawling() throws IOException {
		this.resultList.addAll(processUrl(this.baseURL));
		
		IOUtils.printResults(this.baseURL.toString(), this.keyword, this.resultList);
	}

	public List<String> processUrl(URL url) throws IOException{
		List<String> partialResultList = new ArrayList<>();
		List<String> lines = IOUtils.getHtmlLines(url);
		
		if(findKeyword(lines, keyword)) {
			partialResultList.add(url.toString());
		}
		
		List<URL> internalUrls = findInternalUrls(lines, url.toString());
		
		for(URL internalUrl: internalUrls) {
			partialResultList.addAll(processUrl(internalUrl));
		}
			
		return partialResultList;
	}
	
	private Boolean findKeyword(List<String> lines, String keyword) {
		return lines.stream().anyMatch(a -> {
			return a.contains(keyword);
		});
	}
	
	private List<URL> findInternalUrls(List<String> lines, String url) throws MalformedURLException{
		List<URL> internalUrls = new ArrayList<>();
		
		List<String> linesContainingBaseUrl = lines.stream().filter(line -> line.contains("href")).collect(Collectors.toList());
			
		Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
		for(String line: linesContainingBaseUrl) {
			Matcher m = p.matcher(line);
			
			if(m.find()) {
				for(int i=0; i<m.groupCount(); i++) {
					System.out.println(m.group(i));
					
					//URI uri = new CreateU
					//.add(new URL(m.group(i)));
				}
			}
		}
		
		return internalUrls;
	}
	
	private URL verifyURL(String inputUrl) throws InputException {
		if (inputUrl == null) {
			throw new InputException("Required item: " + inputUrl);
		}

		try {
			URL url = new URL(inputUrl); // Do URL validation.
			url.toURI(); // Do additional URI validations.

			return url;
		} catch (MalformedURLException | URISyntaxException e) {
			throw new InputException(inputUrl, e.getCause());
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
