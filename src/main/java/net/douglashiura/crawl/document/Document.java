package net.douglashiura.crawl.document;

import java.net.URL;

public class Document {

	private String contentLowerCase;
	private URL url;
	private String rawContent;

	public Document(URL url, String content) {
		this.url = url;
		this.rawContent = content;
		contentLowerCase = content.toLowerCase();
	}

	public Boolean find(String queryLowerCase) {
		return contentLowerCase.contains(queryLowerCase);
	}

	public URL getUrl() {
		return url;
	}

	public String rawContent() {
		return rawContent;
	}

}
