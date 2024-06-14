package net.douglashiura.test.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import net.douglashiura.web.IdNotFoundException;
import net.douglashiura.web.QueryStore;

public class QueryStoreTest {

	@Test
	void create() throws Exception {
		QueryStore store = new QueryStore();
		String id = store.getId("Security");
		assertEquals(8, id.length());
	}

	@Test
	void createSame() throws Exception {
		QueryStore store = new QueryStore();
		assertSame(store.getId("Security"), store.getId("security"));
	}

	@Test
	void getQuery() throws Exception {
		QueryStore store = new QueryStore();
		String id = store.getId("Security");
		assertEquals("security", store.getQueryById(id));
	}

	@Test
	void getQueryWithNotId() throws Exception {
		QueryStore store = new QueryStore();
		assertThrows(IdNotFoundException.class, () -> store.getQueryById("NotId"));
	}

	@Test
	void createDistinct() throws Exception {
		QueryStore store = new QueryStore();
		assertNotEquals(store.getId("Security"), store.getId("securit"));
	}
}
