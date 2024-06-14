package net.douglashiura.crawl;

import java.net.URL;
import java.util.Set;

import net.douglashiura.crawl.document.Document;

public interface DocumentProcessable {
	void addProcessedDocument(Document document);
	void process(Set<URL> urls);
}
