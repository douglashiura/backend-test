package net.douglashiura.crawl;

import java.net.URL;
import java.util.List;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import net.douglashiura.crawl.document.Document;

public class URLReader {

	private static final String TEXT_HTML = "text/html";
	private URL url;

	public URLReader(URL url) {
		this.url = url;

	}

	public Document read() throws Exception {
		SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
		HttpClient httpClient = new HttpClient(sslContextFactory);
		httpClient.start();
		Request request = httpClient.newRequest(url.toURI());
		try {
			ContentResponse response = request.send();
			if (isHtml(response)) {
				return new Document(url, response.getContentAsString());
			} else {
				return new Document(url, "");
			}
		} finally {
			httpClient.stop();
			httpClient.destroy();
		}
	}

	private boolean isHtml(ContentResponse response) {
		List<String> headerValues = response.getHeaders().getValuesList(HttpHeader.CONTENT_TYPE);
		for (String value : headerValues) {
			if (value.equalsIgnoreCase(TEXT_HTML)) {
				return true;
			}
		}
		return false;
	}

}
