package com.axreng.backend;

import java.io.IOException;

import com.axreng.backend.webcrawling.CrawlingProcessor;

public class Main {

    public static void main(String[] args) throws IOException {
    	final String BASE_URL = System.getenv("BASE_URL");
    	final String KEYWORD = System.getenv("KEYWORD");
    	final String MAX_RESULTS = System.getenv("MAX_RESULTS");
    	
    	CrawlingProcessor crawlingProcessor = new CrawlingProcessor(BASE_URL, KEYWORD, MAX_RESULTS);
    	crawlingProcessor.executeCrawling();
    	
    }
}
