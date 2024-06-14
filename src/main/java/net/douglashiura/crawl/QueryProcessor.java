package net.douglashiura.crawl;

import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.douglashiura.crawl.document.Document;

public class QueryProcessor {
	private Map<String, Set<URL>> urlsPerQuery;
	private List<Document> documents;

	public QueryProcessor() {
		urlsPerQuery = new LinkedHashMap<String, Set<URL>>();
		documents = Collections.synchronizedList(new LinkedList<Document>());
	}

	public Set<URL> find(String query) {
		query = query.toLowerCase();
		if (urlsPerQuery.containsKey(query)) {
			return urlsPerQuery.get(query);
		} else {
			return findEager(query);
		}
	}

	private synchronized Set<URL> findEager(String query) {
		Set<URL> urlsFound = Collections.synchronizedSet(new HashSet<>());
		for (Document document : documents) {
			if (document.find(query)) {
				urlsFound.add(document.getUrl());
			}
		}
		urlsPerQuery.put(query, urlsFound);
		return urlsFound;
	}

	public void addDocument(Document document) {
		documents.add(document);
		loadPartialsQuery(document);
	}

	private void loadPartialsQuery(Document document) {
		for (Map.Entry<String, Set<URL>> entry : urlsPerQuery.entrySet()) {
			String query = entry.getKey();
			if (document.find(query)) {
				Set<URL> urlsFound = entry.getValue();
				urlsFound.add(document.getUrl());
			}
		}
	}

	public List<Document> getDocuments() {
		return documents;
	}

}
