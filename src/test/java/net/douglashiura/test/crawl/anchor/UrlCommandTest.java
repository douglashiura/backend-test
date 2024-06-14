package net.douglashiura.test.crawl.anchor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;

import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.anchor.UrlCommand;

public class UrlCommandTest {

	@Test
	void withoutCommand() throws Exception {
		assertEquals(new URL("http://example.com/report.html"),	new UrlCommand("http://example.com/", "report.html").getUrl());
	}
	@Test
	void startSlash() throws Exception {
		assertEquals(new URL("http://example.com//report.html"),	new UrlCommand("http://example.com/", "/report.html").getUrl());
	}
	
	@Test
	void startBackCommand() throws Exception {
		assertEquals(new URL("http://example.com/report.html"),	new UrlCommand("http://example.com/any/", "../report.html").getUrl());
	}
	@Test
	void startTwoBackCommand() throws Exception {
		assertEquals(new URL("http://example.com/report.html"),	new UrlCommand("http://example.com/any/master/", "../../report.html").getUrl());
	}

}
