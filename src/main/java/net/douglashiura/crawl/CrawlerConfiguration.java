package net.douglashiura.crawl;

import java.net.URL;

public class CrawlerConfiguration {

	private Integer pagesLimit;
	private Integer crawlerThreadsAmount;
	private URL baseUrl;

	public CrawlerConfiguration(Integer pagesLimit, Integer crawlerThreadsAmount, URL baseUrl) {
		this.pagesLimit = pagesLimit;
		this.crawlerThreadsAmount = crawlerThreadsAmount;
		this.baseUrl = baseUrl;
	}

	public Integer getCrawlerThreadsAmount() {
		return crawlerThreadsAmount;
	}

	public URL getBaseUrl() {
		return baseUrl;
	}

	public Integer getPagesLimit() {
		return pagesLimit;
	}
}
