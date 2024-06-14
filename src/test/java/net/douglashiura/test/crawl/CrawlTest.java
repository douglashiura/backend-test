package net.douglashiura.test.crawl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.Crawl;
import net.douglashiura.crawl.DocumentProcessable;
import net.douglashiura.crawl.document.Document;
@Disabled
public class CrawlTest {

	@Test
	void integration() throws Exception {
		URL baseUrl = new URL("http://hiring.axreng.com/");
		MockProcessor mock = new MockProcessor();
		Crawl crawl = new Crawl(baseUrl, baseUrl, mock);
		
		crawl.run();
		
		assertEquals(1, mock.getDocuments().size());
		assertEquals(3, mock.getUrls().size());
		assertTrue(mock.getUrls().contains(new URL("http://hiring.axreng.com/gfdl.html")));
		assertTrue(mock.getUrls().contains(new URL("http://hiring.axreng.com/archive")));
		assertTrue(mock.getUrls().contains(new URL("http://hiring.axreng.com/manpageindex.html")));
	}

	@Test
	void local() throws Exception {
		URL baseUrl = new URL("http://localhost:10/");
		MockProcessor mock = new MockProcessor();
		Crawl crawl = new Crawl(baseUrl, baseUrl, mock);
		crawl.run();
		assertEquals(1, mock.getDocuments().size());
		assertNull(mock.getUrls());
	}

	class MockProcessor implements DocumentProcessable {

		private Set<URL> urls;
		private List<Document> documents;

		public MockProcessor() {
			documents = new LinkedList<>();
		}

		@Override
		public void addProcessedDocument(Document document) {
			documents.add(document);
		}

		@Override
		public void process(Set<URL> urls) {
			this.urls = urls;
		}

		public List<Document> getDocuments() {
			return documents;
		}

		public Set<URL> getUrls() {
			return urls;
		}
	}

}
