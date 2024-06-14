package net.douglashiura.crawl.anchor;

import java.net.URL;
import java.util.Set;

public interface ExtraUrlProcessable {
	Set<URL> getUrls(String document);
}
