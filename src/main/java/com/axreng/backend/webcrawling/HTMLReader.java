package com.axreng.backend.webcrawling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Stream;

public class HTMLReader {

	public static Stream<String> getHtmlLines(URL url) throws IOException {

		BufferedReader htmlLines = new BufferedReader(new InputStreamReader(url.openStream()));
		Stream<String> lines = htmlLines.lines();
		htmlLines.close();
		
		return lines;
	}
}
