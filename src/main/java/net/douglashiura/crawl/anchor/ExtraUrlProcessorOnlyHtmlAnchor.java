package net.douglashiura.crawl.anchor;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraUrlProcessorOnlyHtmlAnchor implements ExtraUrlProcessable {
	private static final String HTML = ".html";
	private static final String ANCHOR = "<a[^>]+href=\"([^\"]+)\"[^>]*>";
	private Pattern pattern;
	private UrlMounter urlMounter;

	public ExtraUrlProcessorOnlyHtmlAnchor(URL baseUrl, URL page) throws URISyntaxException {
		this.pattern = Pattern.compile(ANCHOR);
		this.urlMounter = new UrlMounter(baseUrl, page);
	}

	public Set<URL> getUrls(String document) {
		Matcher matcher = pattern.matcher(document);
		Set<URL> anchorURLs = new HashSet<>();
		while (matcher.find()) {
			String urlGroup = matcher.group(1);
			addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		}
		return anchorURLs;
	}

	private void addSafeOnlyHtmlAnchor(Set<URL> anchorURLs, String urlGroup) {
		if (urlGroup.toLowerCase().endsWith(HTML)) {
			urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		}
	}

}
