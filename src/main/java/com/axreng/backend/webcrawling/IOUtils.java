package com.axreng.backend.webcrawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
}
