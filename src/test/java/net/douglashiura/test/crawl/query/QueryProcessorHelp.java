package net.douglashiura.test.crawl.query;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.douglashiura.crawl.QueryProcessor;
import net.douglashiura.crawl.document.Document;

public class QueryProcessorHelp {

	public static List<Document> createTenDocuments() throws MalformedURLException {
		List<Document> documents = new ArrayList<Document>(10);
		for (int i = 0; i < 10; i++) {
			URL url = new URL(String.format("http:localhost:%s", i));
			documents.add(new Document(url, "aaaa"));
		}
		return documents;
	}

	public static List<Thread> createThreads(List<Document> tenDocuments, QueryProcessor processor) {
		List<Thread> threads = new ArrayList<>(10);
		for (Document document : tenDocuments) {
			threads.add(new Thread(() -> processor.addDocument(document)));
		}
		return threads;
	}

}
