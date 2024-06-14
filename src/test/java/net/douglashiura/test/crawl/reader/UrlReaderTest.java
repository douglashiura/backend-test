package net.douglashiura.test.crawl.reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.URLReader;
import net.douglashiura.crawl.document.Document;
@Disabled
public class UrlReaderTest {

	@Test
	void getDocumentWithPathCommand() throws Exception {
		URLReader reader = new URLReader(new URL("http://hiring.axreng.com/htmlman8/../htmlman7/capabilities.7.html"));
		Document document = reader.read();
		assertFalse(document.find("privileged"));
		assertFalse(document.find("abracadabra"));
	}

	@Test
	void getDocument() throws Exception {
		URLReader reader = new URLReader(new URL("http://hiring.axreng.com/"));
		Document document = reader.read();
		assertTrue(document.find("bin"));
		assertFalse(document.find("abracadabra"));
	}

	@Test
	void getDocumentNotHtml() throws Exception {
		URLReader reader = new URLReader(new URL("https://www.gov.br/gestao/pt-br/concursonacional/editais/edital-cpnu-bloco-8-10jan2024.pdf"));
		Document document = reader.read();
		assertEquals("", document.rawContent());
	}

	@Test
	void getDocumentFromUrlInvalid() throws Exception {
		URLReader reader = new URLReader(new URL("http://127.0.0.1:10/"));
		assertThrows(ExecutionException.class, () -> reader.read());
	}

	@Test
	void concurrences() throws Exception {
		List<URLReader> tenUrls = UrlCreatorHelp.createTenUrls();
		List<Thread> threads = new ArrayList<>(10);
		List<Document> documents = Collections.synchronizedList(new ArrayList<>(10));
		UrlCreatorHelp.loadThreadsFromUrl(tenUrls, threads, documents);
		for (Thread thread : threads) {
			thread.start();
		}
		for (Thread thread : threads) {
			thread.join();
		}
		assertEquals(10, threads.size());
		assertEquals(10, documents.size());
		for (Document document : documents) {
			assertTrue(document.find("bin"));
			assertFalse(document.find("abracadabra"));
		}

	}

}
