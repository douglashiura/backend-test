package net.douglashiura.web;

import java.security.SecureRandom;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class QueryStore {
	private static final String ALPHA_NUMERIC_LOWERCASE = "0123456789abcdefghijklmnopqrstuvwxyz";
	private static final Random random = new SecureRandom();
	private Map<String, String> tokens;
	private Map<String, String> querys;

	public QueryStore() {
		tokens = new TreeMap<>();
		querys = new TreeMap<>();
	}

	public synchronized String getId(String query) {
		query = query.toLowerCase();
		if (querys.containsKey(query)) {
			return querys.get(query);
		} else {
			String randomToken = generateRandomToken();
			tokens.put(randomToken, query);
			querys.put(query, randomToken);
			return randomToken;
		}
	}

	private String generateRandomToken() {
		StringBuilder token = new StringBuilder(8);
		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(ALPHA_NUMERIC_LOWERCASE.length());
			token.append(ALPHA_NUMERIC_LOWERCASE.charAt(index));
		}
		return token.toString();
	}

	public String getQueryById(String id) throws IdNotFoundException {
		if (tokens.containsKey(id)) {
			return tokens.get(id);
		} else {
			throw new IdNotFoundException();
		}
	}

}
