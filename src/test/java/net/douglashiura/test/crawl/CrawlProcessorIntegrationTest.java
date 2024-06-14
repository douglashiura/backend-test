package net.douglashiura.test.crawl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.CrawlProcessor;
import net.douglashiura.crawl.CrawlerConfiguration;
import net.douglashiura.crawl.QueryProcessor;
@Disabled
public class CrawlProcessorIntegrationTest {

	private CrawlerConfiguration configuration;
	private Set<URL> initial;
	private QueryProcessor queryProcessor;

	@BeforeEach
	void setUp() throws MalformedURLException {
		URL baseUrl = new URL("http://hiring.axreng.com/");
		Integer amountThreadsCrawler = 6;
		Integer pagesLimit = 100;
		configuration = new CrawlerConfiguration(pagesLimit, amountThreadsCrawler, baseUrl);
		initial = new LinkedHashSet<>();
		initial.add(new URL("http://hiring.axreng.com/"));
		queryProcessor = new QueryProcessor();
	}

	@Test
	void processing() throws Exception {
		CrawlProcessor urlCrawl = new CrawlProcessor(configuration, queryProcessor);
		urlCrawl.process(initial);
		Thread.sleep(10);
		assertFalse(urlCrawl.isAllCrawled());
	}

	@Test
	void processingAll() throws Exception {
		CrawlProcessor urlCrawl = new CrawlProcessor(configuration, queryProcessor);
		urlCrawl.process(initial);
		CrawlerHelp.waitForComplete(urlCrawl);
		assertTrue(urlCrawl.isAllCrawled());
		assertEquals(100, urlCrawl.getAllUrls().size());
	}

	@Test
	void processingAllSearching() throws Exception {
		CrawlProcessor urlCrawl = new CrawlProcessor(configuration, queryProcessor);
		urlCrawl.process(initial);
		queryProcessor.find("aaaa");
		Thread.sleep(1000);
		queryProcessor.find("License");
		CrawlerHelp.waitForComplete(urlCrawl);
		assertTrue(urlCrawl.isAllCrawled());
		assertEquals(100, urlCrawl.getAllUrls().size());
		assertEquals(92, queryProcessor.find("License").size());
	}

}
