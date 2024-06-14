package net.douglashiura.test.crawl.anchor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import net.douglashiura.crawl.anchor.UrlMounter;

public class UrlMounterTest {

	@Test
	public void sameHostButNotSamePath() throws MalformedURLException, URISyntaxException {
		String urlGroup = "https://www.example.com/another/page.html";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("https://www.example.com/another/");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/base/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(0, anchorURLs.size());
	}

	@Test
	public void addSafeOnlyHtmlAnchorAbsoluteUrlDifferentHost() throws MalformedURLException, URISyntaxException {
		Set<URL> anchorURLs = new HashSet<>();
		String urlGroup = "https://www.example.co/malicious.html";
		URL page = new URL("https://www.example.com/another/");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(0, anchorURLs.size());
	}

	@Test
	public void relativeUrlFiltred() throws MalformedURLException, URISyntaxException {
		String urlGroup = "page.html";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("https://www.example.com/base/another/");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/base/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(1, anchorURLs.size());
		assertTrue(anchorURLs.contains(new URL("https://www.example.com/base/another/page.html")));
	}

	@Test
	public void relativeUrlAnotherLink() throws MalformedURLException, URISyntaxException {
		String urlGroup = "link";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("https://www.example.com/base/another/");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/base/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(1, anchorURLs.size());
		assertTrue(anchorURLs.contains(new URL("https://www.example.com/base/another/link")));
	}

	@Test
	public void containsRelativeUrlInPageHtml() throws MalformedURLException, URISyntaxException {
		String urlGroup = "page.html";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("https://www.example.com/another/report.html");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/another/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(1, anchorURLs.size());
		assertTrue(anchorURLs.contains(new URL("https://www.example.com/another/page.html")));
	}
	
	@Test
	public void containsRelativeUrl() throws MalformedURLException, URISyntaxException {
		String urlGroup = "page.html";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("https://www.example.com/another/report/");
		UrlMounter urlMounter = new UrlMounter(new URL("https://www.example.com/another/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(1, anchorURLs.size());
		assertTrue(anchorURLs.contains(new URL("https://www.example.com/another/report/page.html")));
	}

	@Test
	public void containsRelativeUrlCommandBack() throws MalformedURLException, URISyntaxException {
		String urlGroup = "../htmlman7/capabilities.7.html";
		Set<URL> anchorURLs = new HashSet<>();
		URL page = new URL("http://hiring.axreng.com/htmlman8/");
		UrlMounter urlMounter = new UrlMounter(new URL("http://hiring.axreng.com/"), page);
		urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		assertEquals(1, anchorURLs.size());
		assertTrue(anchorURLs.contains(new URL("http://hiring.axreng.com/htmlman7/capabilities.7.html")));
	}

}