package net.douglashiura.test.crawl.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.QueryProcessor;
import net.douglashiura.crawl.document.Document;

public class QueryProcessorTest {

	@Test
	void empty() throws Exception {
		QueryProcessor processor = new QueryProcessor();
		assertEquals(0, processor.find("Aaaa").size());
	}

	@Test
	void addingDocumentBefore() throws Exception {
		QueryProcessor processor = new QueryProcessor();
		URL url = new URL("http:localhost");
		processor.addDocument(new Document(url, "aaaa"));
		assertEquals(1, processor.find("Aaaa").size());
		assertTrue(processor.find("Aaaa").contains(url));
	}

	@Test
	void addingDocumentAfter() throws Exception {
		QueryProcessor processor = new QueryProcessor();
		URL url = new URL("http:localhost");
		processor.find("Aaaa");
		processor.addDocument(new Document(url, "aaaa"));
		assertEquals(1, processor.find("Aaaa").size());
		assertTrue(processor.find("Aaaa").contains(url));
	}

	@Test
	void addingDocumentConcurret() throws Exception {
		QueryProcessor processor = new QueryProcessor();
		List<Document> tenDocuments = QueryProcessorHelp.createTenDocuments();
		List<Thread> tenThreads = QueryProcessorHelp.createThreads(tenDocuments, processor);
		for (Thread thread : tenThreads) {
			thread.start();
		}
		for (Thread thread : tenThreads) {
			thread.join();
		}
		assertEquals(10, processor.find("Aaaa").size());
	}

	@Test
	void addingDocumentStartConcurret() throws Exception {
		QueryProcessor processor = new QueryProcessor();
		List<Document> tenDocuments = QueryProcessorHelp.createTenDocuments();
		List<Thread> tenThreads = QueryProcessorHelp.createThreads(tenDocuments, processor);
		processor.find("Aaaa");
		for (Thread thread : tenThreads) {
			thread.start();
		}
		for (Thread thread : tenThreads) {
			thread.join();
		}
		assertEquals(10, processor.find("Aaaa").size());
	}

}
