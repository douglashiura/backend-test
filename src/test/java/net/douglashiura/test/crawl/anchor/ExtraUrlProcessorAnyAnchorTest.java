package net.douglashiura.test.crawl.anchor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.anchor.ExtraUrlProcessable;
import net.douglashiura.crawl.anchor.ExtraUrlProcessorAnyAnchor;

public class ExtraUrlProcessorAnyAnchorTest {

	private ExtraUrlProcessable processor;

	@BeforeEach
	void setUp() throws MalformedURLException, URISyntaxException {
		processor = new ExtraUrlProcessorAnyAnchor(new URL("https://example.com/"), new URL("https://example.com/report/"));
	}

	@Test
	void indexAdding() throws Exception {
		String xhtmlAnchor = "<a href=\"https://example.com/\">";
		Set<URL> urls = processor.getUrls(xhtmlAnchor);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/"), urls.iterator().next());
	}

	@Test
	void xhtmlAdding() throws Exception {
		String xhtmlAnchor = "<a href=\"https://example.com/page1.xhtml\">";
		Set<URL> urls = processor.getUrls(xhtmlAnchor);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/page1.xhtml"), urls.iterator().next());
	}

	@Test
	void simpleUrl() throws Exception {
		String onlyAnchor = "<a href=\"https://example.com/page1.html\">";
		Set<URL> urls = processor.getUrls(onlyAnchor);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/page1.html"), urls.iterator().next());
	}

	@Test
	void discardedUrl() throws Exception {
		String onlyAnchor = "<a href=\"https://example.co/page1.html\">";
		Set<URL> urls = processor.getUrls(onlyAnchor);
		assertEquals(0, urls.size());
	}

	@Test
	void distinctAnchors() throws Exception {
		String twoAnchors = " <a href=\"https://example.com/page.html\">...<a href=\"page.html\">";
		Set<URL> urls = processor.getUrls(twoAnchors);
		assertEquals(2, urls.size());
		assertTrue(urls.contains(new URL("https://example.com/page.html")));
		assertTrue(urls.contains(new URL("https://example.com/report/page.html")));
	}

	@Test
	void duplicatedAnchor() throws Exception {
		String twoAnchors = " <a href=\"https://example.com/report/page1.html\">...<a href=\"page1.html\">";
		Set<URL> urls = processor.getUrls(twoAnchors);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/report/page1.html"), urls.iterator().next());
	}

	@Test
	void relativeUrl() throws Exception {
		String relativeUrl = "<a href=\"page1.html\">";
		Set<URL> urls = processor.getUrls(relativeUrl);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/report/page1.html"), urls.iterator().next());
	}

	@Test
	void relativeUrlUpperCase() throws Exception {
		String relativeUrl = "<a href=\"PAGE1.HTML\">";
		Set<URL> urls = processor.getUrls(relativeUrl);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/report/PAGE1.HTML"), urls.iterator().next());
	}

	@Test
	void relativeUrlStartWithSlash() throws Exception {
		String document = "<a href=\"/page1.html\">";
		Set<URL> urls = processor.getUrls(document);
		assertEquals(1, urls.size());
		assertEquals(new URL("https://example.com/report//page1.html"), urls.iterator().next());
	}

	@Test
	void noExtraUrl() throws Exception {
		String emptyDocument = "";
		Set<URL> urls = processor.getUrls(emptyDocument);
		assertEquals(0, urls.size());

	}

}
