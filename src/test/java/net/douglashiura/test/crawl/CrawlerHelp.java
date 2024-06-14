package net.douglashiura.test.crawl;

import net.douglashiura.crawl.CrawlProcessor;

public class CrawlerHelp {

	public static void waitForComplete(CrawlProcessor urlCrawl) throws InterruptedException {
		while (!urlCrawl.isAllCrawled()) {
			Thread.sleep(100);
		}
	}

}
