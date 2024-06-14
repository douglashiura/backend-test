package net.douglashiura.test.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jetty.http.HttpHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;

import net.douglashiura.crawl.CrawlProcessor;
import net.douglashiura.crawl.CrawlerConfiguration;
import net.douglashiura.crawl.QueryProcessor;
import net.douglashiura.web.FinderService;
import net.douglashiura.web.QueryStore;
import spark.Spark;
@Disabled
public class FinderServiceTest {

	private HttpClient client;
	private QueryStore queryStore;
	private CrawlProcessor crawler;
	private QueryProcessor queryProcessor;

	@BeforeEach
	void setUp() throws MalformedURLException {
		URL baseUrl = new URL("http://hiring.axreng.com/");
		Integer pagesLimit = 100;
		Integer amountThreadsCrawler = 2;
		CrawlerConfiguration configuration = new CrawlerConfiguration(pagesLimit, amountThreadsCrawler, baseUrl);
		queryProcessor = new QueryProcessor();
		crawler = new CrawlProcessor(configuration, queryProcessor);
		queryStore = new QueryStore();

		client = HttpClient.newHttpClient();
	}

	@SuppressWarnings("unchecked")
	@Test
	void done() throws Exception {
		FinderService service = new FinderService(queryProcessor, queryStore, crawler);
		Spark.get("/done/:id", (request, response) -> service.find(request, response));
		String id = queryStore.getId("Pesquisa");
		String urlTest = String.format("http://localhost:4567/done/%s", id);
		URI targetURI = URI.create(urlTest);
		HttpRequest request = HttpRequest.newBuilder().GET().uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		Gson gson = new Gson();
		Map<?, ?> map = gson.fromJson(response.body(), Map.class);
		List<String> urls = (List<String>) map.get("urls");
		assertEquals(200, response.statusCode());
		assertEquals(id, map.get("id"));
		assertEquals("done", map.get("status"));
		assertEquals(0, urls.size());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

	@SuppressWarnings("unchecked")
	@Test
	void active() throws Exception {
		FinderService service = new FinderService(queryProcessor, queryStore, crawler);
		Spark.get("/active/:id", (request, response) -> service.find(request, response));
		Set<URL> addendum = new LinkedHashSet<>();
		addendum.add(new URL("http://hiring.axreng.com/gfdl-addendum.html"));
		crawler.process(addendum);
		Thread.sleep(2000);
		String id = queryStore.getId("Documentation");
		String urlTest = String.format("http://localhost:4567/active/%s", id);
		URI targetURI = URI.create(urlTest);
		HttpRequest request = HttpRequest.newBuilder().GET().uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		Gson gson = new Gson();
		Map<?, ?> map = gson.fromJson(response.body(), Map.class);
		List<String> urls = (List<String>) map.get("urls");
		assertEquals(200, response.statusCode());
		assertEquals(id, map.get("id"));
		assertEquals("active", map.get("status"));
		assertTrue(urls.size() > 0);
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}
	
	@Test
	void idNotFound() throws Exception {
		FinderService service = new FinderService(queryProcessor, queryStore, crawler);
		Spark.get("/error/:id", (request, response) -> service.find(request, response));
		String urlTest = "http://localhost:4567/error/aaaaaaaa";
		URI targetURI = URI.create(urlTest);
		HttpRequest request = HttpRequest.newBuilder().GET().uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		assertEquals(400, response.statusCode());
		assertEquals("{\"error\":\"Id not found.\"}", response.body());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

}
