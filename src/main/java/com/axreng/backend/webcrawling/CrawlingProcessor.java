package com.axreng.backend.webcrawling;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.axreng.backend.exceptions.InputException;

public class CrawlingProcessor {

	private URL baseUrl;
	private String keyword;
	private Integer maxResults;
	private Set<String> visitedUrls;
	private Set<String> findedUrls;

	private Set<String> resultSet;
	
	private IOUtils ioUtils;

	public CrawlingProcessor(String baseUrl, String keyword, Integer maxResults) throws InputException {
		this.ioUtils = new IOUtils();
		
		this.baseUrl = this.ioUtils.verifyBaseURL(baseUrl);
		this.keyword = this.ioUtils.verifyKeyword(keyword);
		this.maxResults = this.ioUtils.verifyMaxResults(maxResults);
		
		this.visitedUrls = new HashSet<>();
		this.findedUrls = new HashSet<>();
		this.resultSet = new HashSet<>();
	}

	public void executeCrawling() throws IOException {
		processUrl(this.baseUrl);
		
		this.ioUtils.printResults(this.baseUrl.toString(), this.keyword, this.resultSet);
	}

	private void processUrl(URL url) throws IOException{
		if(this.maxResults != -1 && resultSet.size() >= this.maxResults) {
			return;
		}
		
		List<String> lines = this.ioUtils.getHtmlLines(url);
		
		if(findKeyword(lines, keyword)) {
			this.resultSet.add(url.toString());
		}
		
		Map<String, URL> internalUrls = findInternalUrls(lines, url.toString());
		
		for(URL internalUrl: internalUrls.values()) {
			processUrl(internalUrl);
		}
		
		System.out.println("Visitado: -> "+ url.toString());
		visitedUrls.add(url.toString());
	}
	
	private Boolean findKeyword(List<String> lines, String keyword) {	
		Pattern p = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);	
		return lines.stream().anyMatch(line -> p.matcher(line).find());
	}
	
	private Map<String, URL> findInternalUrls(List<String> lines, String baseUrl) throws MalformedURLException{
		Map<String, URL> internalUrls = new HashMap<>();
		List<String> linesContainingBaseUrl = lines.stream().filter(line -> line.contains("href")).collect(Collectors.toList());
			
		Pattern p = Pattern.compile("href=\"(.*?)\"", Pattern.DOTALL);
		for(String line: linesContainingBaseUrl) {
			Matcher m = p.matcher(line);
			
			while(m.find()) {
				String urlStr = m.group().split("\"")[1];
				if(!urlStr.endsWith(".html") || urlStr.startsWith("mailto")) {
					continue;
				}
				URI uri = URI.create(urlStr);
				
				URL url = verifyUri(uri, baseUrl);
				
				if(url == null) {
					continue;
				}
				
				urlStr = url.toString();
				if(!this.findedUrls.contains(urlStr) && !this.visitedUrls.contains(urlStr) && !internalUrls.containsKey(urlStr)) {
					internalUrls.put(urlStr, url);
					this.findedUrls.add(urlStr);
				}
			}
		}
		return internalUrls;
	}
	
	private URL verifyUri(URI uri, String baseUrl) throws MalformedURLException {
		String uriStr = uri.toString();
		
		if(uri.isAbsolute()) {
			if(uriStr.contains(baseUrl)) {
				return new URL(uri.toString());
			}
		}else if(uriStr.startsWith("../")){
			return new URL(baseUrl+uri.toString().replace("../", ""));
		}else {
			return new URL(this.baseUrl+uri.toString());
		}
		
		return null;
	}

	public Integer getMaxResults() {
		return maxResults;
	}
	
	public Set<String> getResultSet(){
		return this.resultSet;
	}
}
