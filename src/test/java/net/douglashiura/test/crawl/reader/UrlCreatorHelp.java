package net.douglashiura.test.crawl.reader;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.douglashiura.crawl.URLReader;
import net.douglashiura.crawl.document.Document;

public class UrlCreatorHelp {

	public static List<URLReader> createTenUrls() throws MalformedURLException {
		List<URLReader> tenUrls = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			tenUrls.add(new URLReader(new URL("http://hiring.axreng.com/")));
		}
		return tenUrls;
	}

	public static void loadThreadsFromUrl(List<URLReader> tenUrls, List<Thread> threads, List<Document> documents) {
		for (URLReader urlReader : tenUrls) {
			threads.add(new Thread(() -> {
				try {
					documents.add(urlReader.read());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}));
		}
	}

}
