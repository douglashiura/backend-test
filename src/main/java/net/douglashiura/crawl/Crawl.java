package net.douglashiura.crawl;

import java.net.URL;
import java.util.Set;

import net.douglashiura.crawl.anchor.ExtraUrlProcessable;
import net.douglashiura.crawl.anchor.ExtraUrlProcessorAnyAnchor;
import net.douglashiura.crawl.document.Document;

public class Crawl implements Runnable {
	private URL url;
	private DocumentProcessable processor;
	private URL baseUrl;

	public Crawl(URL baseUrl, URL url, DocumentProcessable processor) {
		this.baseUrl = baseUrl;
		this.url = url;
		this.processor = processor;
	}

	@Override
	public void run() {
		try {
			Document document = new URLReader(url).read();
			ExtraUrlProcessable crawlUrls = new ExtraUrlProcessorAnyAnchor(baseUrl, url);
			Set<URL> urls = crawlUrls.getUrls(document.rawContent());
			processor.process(urls);
			processor.addProcessedDocument(document);
		} catch (Exception inFail) {
			processor.addProcessedDocument(new Document(url, ""));
		}
	}
}