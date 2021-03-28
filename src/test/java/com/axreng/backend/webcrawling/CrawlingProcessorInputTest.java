package com.axreng.backend.webcrawling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.axreng.backend.exceptions.InputException;

public class CrawlingProcessorInputTest {

	/*
	 * Should fail: URL not present.
	 */
	@Test
	public void urlNotPresentTest(){	
		assertThrows(InputException.class, () -> new CrawlingProcessor(null, "four", -1));	
	}
	
	/*
	 * Should fail: Keyword not present.
	 */
	@Test
	public void keyWordNotPresentTest(){	
		assertThrows(InputException.class, () -> new CrawlingProcessor("http://hiring.axreng.com/", null, -1));	
	}
	
	/*
	 * Should fail: No protocol in the URL.
	 */
	@Test
	public void noProtocolTest(){
		assertThrows(InputException.class, () -> new CrawlingProcessor("hiring.axreng.com/", "four", -1));	
	}
	
	/*
	 * Should fail: Invalid URL.
	 */
	@Test
	public void invalidUrlTest(){
		assertThrows(InputException.class, () -> new CrawlingProcessor("http://hiri%ng.axreng.com/", "four", -1));	
	}
	
	/*
	 * Should fail: Invalid Keyword due length < 4 or length > 32.
	 */
	@Test
	public void invalidKeywordSizeTest01(){
		assertThrows(InputException.class, () -> new CrawlingProcessor("http://hiring.axreng.com/", "fou", -1));	
		assertThrows(InputException.class, () -> new CrawlingProcessor("http://hiring.axreng.com/", "fourfourfourfourfourfourfourfourf", -1));
	}
	
	/*
	 * Should fail: Invalid Keyword due non alphanumeric character.
	 */
	@Test
	public void invalidKeywordNotAlphaNumeric(){
		assertThrows(InputException.class, () -> new CrawlingProcessor("http://hiring.axreng.com/", "fou&", -1));	
	}
	
	/*
	 * Should Pass with no max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor01() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "fou4", -1);	
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	/*
	 * Should Pass with max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor02() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", 10);	
		
		assertEquals(10, cp.getMaxResults());
	}
	
	/*
	 * Should Pass and convert max results to -1 due an invalid number input.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor03() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", -10);
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	/*
	 * Should Pass and convert max results to -1 due an invalid number input.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor04() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", 0);
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	
}
