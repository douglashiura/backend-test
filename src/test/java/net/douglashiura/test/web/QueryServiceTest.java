package net.douglashiura.test.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Map;

import org.eclipse.jetty.http.HttpHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.douglashiura.web.QueryService;
import net.douglashiura.web.QueryStore;
import spark.Spark;

public class QueryServiceTest {

	private HttpClient client;

	@BeforeEach
	void setUp() {
		QueryStore queryStore = new QueryStore();
		QueryService service = new QueryService(queryStore);
		Spark.post("/test", (request, response) -> service.getId(request, response));
		client = HttpClient.newHttpClient();
	}

	@Test
	void ok() throws Exception {
		URI targetURI = URI.create("http://localhost:4567/test");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("keyword", "Licence");
		BodyPublisher body = BodyPublishers.ofString(jsonObject.toString());
		HttpRequest request = HttpRequest.newBuilder().POST(body).uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		Gson gson = new Gson();
		Map<?, ?> map = gson.fromJson(response.body(), Map.class);
		assertEquals(200, response.statusCode());
		assertEquals(8, map.get("id").toString().length());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

	@Test
	void badRequestSmall() throws Exception {
		URI targetURI = URI.create("http://localhost:4567/test");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("keyword", "123");
		BodyPublisher body = BodyPublishers.ofString(jsonObject.toString());
		HttpRequest request = HttpRequest.newBuilder().POST(body).uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		assertEquals(400, response.statusCode());
		assertEquals("{\"error\":\"Keyword size invalid.\"}", response.body());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

	@Test
	void badRequestNull() throws Exception {
		URI targetURI = URI.create("http://localhost:4567/test");
		JsonObject jsonObject = new JsonObject();
		BodyPublisher body = BodyPublishers.ofString(jsonObject.toString());
		HttpRequest request = HttpRequest.newBuilder().POST(body).uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		assertEquals(400, response.statusCode());
		assertEquals("{\"error\":\"Missing keyword.\"}", response.body());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

	@Test
	void badRequestLarger() throws Exception {
		URI targetURI = URI.create("http://localhost:4567/test");
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("keyword", "012345678901234567890123456789123");
		BodyPublisher body = BodyPublishers.ofString(jsonObject.toString());
		HttpRequest request = HttpRequest.newBuilder().POST(body).uri(targetURI).build();
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		HttpHeaders headers = response.headers();
		assertEquals(400, response.statusCode());
		assertEquals("{\"error\":\"Keyword size invalid.\"}", response.body());
		assertEquals("application/json", headers.firstValue(HttpHeader.CONTENT_TYPE.asString()).get());
	}

}
