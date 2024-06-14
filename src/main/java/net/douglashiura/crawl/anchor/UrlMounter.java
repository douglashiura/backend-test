package net.douglashiura.crawl.anchor;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

public class UrlMounter {

	private static final String SLASH = "/";
	private URL base;
	private URI pageUrl;

	public UrlMounter(URL baseUrl, URL page) throws URISyntaxException {
		this.base = baseUrl;
		this.pageUrl = getPageurl(page);
	}

	private URI getPageurl(URL page) throws URISyntaxException {
		String asciiUrl = page.toURI().toASCIIString();
		int lastIndexOf = asciiUrl.lastIndexOf(SLASH);
		URI uri = URI.create(asciiUrl.substring(0, lastIndexOf));
		return uri;
	}

	public void addSafeOnlyHtmlAnchor(Set<URL> anchorURLs, String path) {
		try {
			URL url = new URL(path);
			addFilteringByBase(anchorURLs, url);
		} catch (MalformedURLException potentialRelativeURL) {
			addingBaseSafe(anchorURLs, path);
		} catch (URISyntaxException invalidUri) {
			System.err.println(invalidUri.getMessage());
		}
	}

	private void addFilteringByBase(Set<URL> anchorURLs, URL url) throws URISyntaxException {
		if (url.toURI().toASCIIString().startsWith(base.toString())) {
			anchorURLs.add(url);
		}
	}

	private void addingBaseSafe(Set<URL> anchorURLs, String path) {
		try {
			UrlCommand urlCommand = new UrlCommand(pageUrl.toASCIIString(), path);
			addFilteringByBase(anchorURLs, urlCommand.getUrl());
		} catch (MalformedURLException | URISyntaxException incomprehensibleURL) {
			incomprehensibleURL.printStackTrace();
		}
	}

}
