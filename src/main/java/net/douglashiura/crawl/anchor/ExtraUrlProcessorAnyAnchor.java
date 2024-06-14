package net.douglashiura.crawl.anchor;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraUrlProcessorAnyAnchor implements ExtraUrlProcessable {
	private static final String ANCHOR = "<a[^>]+href=\"([^\"]+)\"[^>]*>";
	private Pattern pattern;
	private UrlMounter urlMounter;

	public ExtraUrlProcessorAnyAnchor(URL baseUrl, URL page) throws URISyntaxException {
		this.urlMounter = new UrlMounter(baseUrl, page);
		this.pattern = Pattern.compile(ANCHOR);
	}

	public Set<URL> getUrls(String document) {
		Matcher matcher = pattern.matcher(document);
		Set<URL> anchorURLs = new HashSet<>();
		while (matcher.find()) {
			String urlGroup = matcher.group(1);
			urlMounter.addSafeOnlyHtmlAnchor(anchorURLs, urlGroup);
		}
		return anchorURLs;
	}

}
