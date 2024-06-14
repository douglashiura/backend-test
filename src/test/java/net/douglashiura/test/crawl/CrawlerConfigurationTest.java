package net.douglashiura.test.crawl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URL;

import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.CrawlerConfiguration;

public class CrawlerConfigurationTest {

	@Test
	void get() throws Exception {
		URL baseUrl = new URI("https://google.com/").toURL();
		Integer amountThreadsCrawler = 6;
		Integer pagesLimit = 100;
		CrawlerConfiguration configuration;
		configuration = new CrawlerConfiguration(pagesLimit, amountThreadsCrawler, baseUrl);
		assertEquals(new URL("https://google.com/"), configuration.getBaseUrl());
		assertEquals(6, configuration.getCrawlerThreadsAmount());
		assertEquals(100, configuration.getPagesLimit());
	}

	@Test
	void url() throws Exception {
		URL home = new URI("https://google.com/").toURL();
		assertEquals("https://google.com/", home.toString());
	}

}
