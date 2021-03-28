package com.axreng.backend;

import java.io.IOException;

import com.axreng.backend.webcrawling.CrawlingProcessor;

public class Main {

    public static void main(String[] args) throws IOException {
        
    	CrawlingProcessor crawlingProcessor = new CrawlingProcessor("http://hiring.axreng.com", "four", -1);
    	crawlingProcessor.executeCrawling();
    }
}
