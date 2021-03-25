package com.axreng.backend;

import com.axreng.backend.webcrawling.WebNavigator;

public class Main {

    public static void main(String[] args) {
        
    	WebNavigator webNavigator = new WebNavigator("http://hiring.axreng.com/", "four");
    	webNavigator.navigate();
    }
}
