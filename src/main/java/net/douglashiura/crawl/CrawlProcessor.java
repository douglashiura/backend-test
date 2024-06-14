package net.douglashiura.crawl;

import java.net.URL;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.douglashiura.crawl.document.Document;

public class CrawlProcessor implements DocumentProcessable {
	private ExecutorService pool;
	private Set<String> allUrls;
	private CrawlerConfiguration configuration;
	private QueryProcessor queryProcessor;

	public CrawlProcessor(CrawlerConfiguration configuration, QueryProcessor queryProcessor) {
		this.configuration = configuration;
		this.queryProcessor = queryProcessor;
		this.allUrls = new TreeSet<>();
		this.pool = Executors.newFixedThreadPool(configuration.getCrawlerThreadsAmount());
	}

	@Override
	public synchronized void process(Set<URL> urls) {
		for (URL url : urls) {
			if (shouldProcess(url)) {
				allUrls.add(url.toString());
				pool.execute(new Crawl(configuration.getBaseUrl(), url, this));
			}
		}
	}

	private boolean shouldProcess(URL url) {
		return allUrls.size() < configuration.getPagesLimit() && !allUrls.contains(url.toString());
	}

	@Override
	public void addProcessedDocument(Document document) {
		queryProcessor.addDocument(document);
	}

	public Set<String> getAllUrls() {
		return allUrls;
	}

	public Boolean isAllCrawled() {
		return allUrls.size() == queryProcessor.getDocuments().size();
	}

}
