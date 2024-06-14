package net.douglashiura.crawl.anchor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class UrlCommand {

	private static final String COMMAND = "..";
	private static final String SLASH = "/";
	private String url;

	public UrlCommand(String baseUrl, String relativePath) {
		url = buildUrlWithParentDirectory(baseUrl, relativePath);
	}

	public URL getUrl() throws MalformedURLException {
		return new URL(url);
	}

	public String buildUrlWithParentDirectory(String baseUrl, String relativePath) {
		if (!baseUrl.endsWith(SLASH)) {
			baseUrl += SLASH;
		}
		String[] segments = relativePath.split(SLASH);
		if (segments.length > 0 && segments[segments.length - 1].isEmpty()) {
			segments = Arrays.copyOf(segments, segments.length - 1);
		}
		int parentDirectoryLevels = 0;
		for (String segment : segments) {
			if (segment.equals(COMMAND)) {
				parentDirectoryLevels++;
			} else {
				break;
			}
		}
		String newUrl = baseUrl;
		for (int i = 0; i < parentDirectoryLevels; i++) {
			newUrl = newUrl.substring(0, newUrl.lastIndexOf(SLASH));
		}
		newUrl = newUrl.substring(0, newUrl.lastIndexOf(SLASH));
		for (String segment : segments) {
			if (!segment.equals(COMMAND)) {
				newUrl += SLASH + segment;
			}
		}
		return newUrl;
	}

}
