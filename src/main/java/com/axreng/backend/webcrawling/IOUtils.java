package com.axreng.backend.webcrawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.axreng.backend.exceptions.InputException;

public class IOUtils {

	public List<String> getHtmlLines(URL url) throws IOException {

		BufferedReader htmlLines = new BufferedReader(new InputStreamReader(url.openStream()));
		Stream<String> lines = htmlLines.lines();
		
		return lines.collect(Collectors.toList());
	}
	
	public void printResults(String baseUrl, String keyword, Set<String> resultList) {
		System.out.println("Search starting with base URL " + baseUrl + " and keyword '" + keyword +"'" );
		
		resultList.forEach(result -> System.out.println("Result found: "+ result));

		System.out.println("Search finished with "+ resultList.size() + " results found");
	}
	
	public URL verifyBaseURL(String inputUrl) throws InputException {
		if (inputUrl == null) {
			throw new InputException("Required item: " + inputUrl);
		}

		try {
			if(!inputUrl.endsWith("/")) {
				inputUrl = inputUrl+ "/";
			}
			
			URL url = new URL(inputUrl); // Do URL validation.
			url.toURI(); // Do additional URI validations.

			return url;
		} catch (MalformedURLException | URISyntaxException e) {
			throw new InputException(inputUrl, e.getCause());
		}
	}

	public String verifyKeyword(String keyword) throws InputException {
		if (keyword == null) {
			throw new InputException("Required item: " + keyword);
		}else if(!keyword.matches("^[a-zA-Z0-9_]*$")) {
			throw new InputException("Invalid keyword, should not contain non alphanumeric characters.");
		} else if (keyword.length() < 4 || keyword.length() > 32) {
			throw new InputException(keyword.length());
		}
		
		return keyword;
	}
	
	public Integer verifyMaxResults(Integer maxResults) {
		if(maxResults == null || maxResults == 0 || maxResults < -1){
			return -1;
		}
		
		return maxResults;
	}
}
