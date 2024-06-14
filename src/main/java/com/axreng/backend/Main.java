package com.axreng.backend;

import static spark.Spark.get;
import static spark.Spark.post;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashSet;
import java.util.Set;

import net.douglashiura.crawl.CrawlProcessor;
import net.douglashiura.crawl.CrawlerConfiguration;
import net.douglashiura.crawl.QueryProcessor;
import net.douglashiura.web.FinderService;
import net.douglashiura.web.QueryService;
import net.douglashiura.web.QueryStore;

public class Main {
	private QueryService queryService;
	private FinderService finderService;

	public Main() throws MalformedURLException {
		String baseUrlRaw = System.getenv("BASE_URL");
		assertUrlValid(baseUrlRaw);
		URL baseUrl = new URL(baseUrlRaw);
		Integer pagesLimit = 1000000;
		Integer amountThreadsCrawler = Runtime.getRuntime().availableProcessors() * 2;
		CrawlerConfiguration configuration = new CrawlerConfiguration(pagesLimit, amountThreadsCrawler, baseUrl);
		QueryProcessor queryProcessor = new QueryProcessor();
		CrawlProcessor crawlerProcessor = new CrawlProcessor(configuration, queryProcessor);
		Set<URL> base = new LinkedHashSet<>();
		base.add(new URL(baseUrlRaw));
		crawlerProcessor.process(base);
		QueryStore queryStore = new QueryStore();
		queryService = new QueryService(queryStore);
		finderService = new FinderService(queryProcessor, queryStore, crawlerProcessor);
	}

	public static void main(String[] args) throws MalformedURLException {
		Main main = new Main();
		get("/crawl/:id", (request, response) -> main.getFinderService().find(request, response));
		post("/crawl", (request, response) -> main.getQueryService().getId(request, response));
	}

	public QueryService getQueryService() {
		return queryService;
	}

	public FinderService getFinderService() {
		return finderService;
	}

	private void assertUrlValid(String baseUrl) throws MalformedURLException {
		if (!baseUrl.endsWith("/")) {
			throw new MalformedURLException();
		}
	}
}
