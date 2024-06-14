package net.douglashiura.test.crawl.document;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.document.Document;

public class DocumentFinderTest {

	private URL url;

	@BeforeEach
	void setUp() throws MalformedURLException {
		url = new URL("http://hiring.axreng.com/");
	}

	@Test
	void empty() throws Exception {
		Document empty = new Document(url, "");
		assertFalse(empty.find("security"));
	}

	@Test
	void security() throws Exception {
		Document empty = new Document(url, "SecUrity");
		assertTrue(empty.find("security"));
	}

	@Test
	void socialSecurityPartial() throws Exception {
		Document empty = new Document(url, " socia SecUrity");
		assertFalse(empty.find("social SecUrity"));
	}

	@Test
	void socialSecurityComplete() throws Exception {
		Document empty = new Document(url, " Social SecUrity ");
		assertTrue(empty.find("social security"));
	}

	@Test
	void socialSecurityWitBlank() throws Exception {
		Document empty = new Document(url, "Social SecUrity");
		assertFalse(empty.find("social security "));
	}
}
