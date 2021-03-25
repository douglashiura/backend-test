package com.axreng.backend.webcrawling;

import java.net.URI;

public class WebNavigator {

	private String baseUrl;
	private String keyword;
	
	public WebNavigator(String baseUrl, String keyword) {
		this.baseUrl = baseUrl;
		this.keyword = keyword;
	}
	
	public void navigate() {	
		URI uri = URI.create(this.baseUrl);
		System.out.println(uri);	
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
