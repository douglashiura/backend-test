package net.douglashiura.web;

import java.util.Objects;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.http.HttpStatus;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.Request;
import spark.Response;

public class QueryService {

	private QueryStore queryStore;

	public QueryService(QueryStore queryStore) {
		this.queryStore = queryStore;
	}

	public Object getId(Request request, Response response) {
		response.header(HttpHeader.CONTENT_TYPE.asString(), "application/json");
		Gson gson = new Gson();
		QueryRequest query = gson.fromJson(request.body(), QueryRequest.class);
		JsonObject validate = validate(query, response);
		if (validate==null) {
			JsonObject object = new JsonObject();
			object.addProperty("id", queryStore.getId(query.getKeyword()));
			response.body(object.toString());
			return object;
		}
		response.status(HttpStatus.BAD_REQUEST_400);
		return validate;
	}

	private JsonObject validate(QueryRequest query, Response response) {
		if (Objects.isNull(query.getKeyword())) {
			JsonObject object = new JsonObject();
			object.addProperty("error", "Missing keyword.");
			return object;
		} else if (query.getKeyword().length() < 4 || query.getKeyword().length() > 32) {
			JsonObject object = new JsonObject();
			object.addProperty("error", "Keyword size invalid.");
			return object;
		}
		return null;
	}

}
