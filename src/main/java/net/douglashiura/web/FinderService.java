package net.douglashiura.web;

import java.net.URL;
import java.util.Set;

import org.eclipse.jetty.http.HttpHeader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.douglashiura.crawl.CrawlProcessor;
import net.douglashiura.crawl.QueryProcessor;
import spark.Request;
import spark.Response;

public class FinderService {

	private QueryProcessor queryProcessor;
	private QueryStore queryStore;
	private CrawlProcessor processor;

	public FinderService(QueryProcessor queryProcessor, QueryStore queryStore, CrawlProcessor processor) {
		this.queryProcessor = queryProcessor;
		this.queryStore = queryStore;
		this.processor = processor;
	}

	public Object find(Request request, Response response) {
		response.header(HttpHeader.CONTENT_TYPE.asString(), "application/json");
		try {
			Boolean done = processor.isAllCrawled();
			String id = request.params("id");
			String query = queryStore.getQueryById(id);
			Set<URL> urlsResponse = queryProcessor.find(query);
			JsonObject responseObject = new JsonObject();
			responseObject.addProperty("id", id);
			responseObject.addProperty("status", done ? "done" : "active");
			JsonArray urls = new JsonArray(urlsResponse.size());
			for (URL url : urlsResponse) {
				urls.add(url.toString());
			}
			responseObject.add("urls", urls);
			return responseObject;
		} catch (IdNotFoundException e) {
			response.status(400);
			JsonObject responseObject = new JsonObject();
			responseObject.addProperty("error", "Id not found.");
			return responseObject;
		}
	}

}
