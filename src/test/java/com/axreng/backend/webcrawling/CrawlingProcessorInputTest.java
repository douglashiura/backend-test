package com.axreng.backend.webcrawling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.axreng.backend.exceptions.InputException;

public class CrawlingProcessorInputTest {

	private static IOUtils ioUtils;
	
	@BeforeAll
	public static void setUp() {
		ioUtils = new IOUtils();
	}
	
	/*
	 * Should fail: URL not present.
	 */
	@Test
	public void urlNotPresentTest(){	
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL(null));	
	}
	
	/*
	 * Should fail: Keyword not present.
	 */
	@Test
	public void keyWordNotPresentTest(){	
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword(null));	
	}
	
	/*
	 * Should fail: No protocol in the URL.
	 */
	@Test
	public void noProtocolTest(){
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL("hiring.axreng.com/"));	
	}
	
	/*
	 * Should fail: Invalid URL.
	 */
	@Test
	public void invalidUrlTest(){
		assertThrows(InputException.class, () -> ioUtils.verifyBaseURL("http://hiri%ng.axreng.com/"));	
	}
	
	/*
	 * Should fail: Invalid Keyword due length < 4 or length > 32.
	 */
	@Test
	public void invalidKeywordSizeTest01(){
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fou"));	
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fourfourfourfourfourfourfourfourf"));
	}
	
	/*
	 * Should fail: Invalid Keyword due non alphanumeric character.
	 */
	@Test
	public void invalidKeywordNotAlphaNumeric(){
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fou&"));
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fo.ur"));	
		assertThrows(InputException.class, () -> ioUtils.verifyKeyword("fo ur"));	
	}
	
	/*
	 * Should Pass with no max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor01() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "fou4", "-1");	
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	/*
	 * Should Pass with no max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor02() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "fou4", null);	
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	/*
	 * Should Pass with max results.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor03() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", "10");	
		
		assertEquals(10, cp.getMaxResults());
	}
	
	/*
	 * Should Pass and convert max results to -1 due an invalid number input.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor04() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", "-10");
		
		assertEquals(-1, cp.getMaxResults());
	}
	
	/*
	 * Should Pass and convert max results to -1 due an invalid number input.
	 */
	@Test
	public void shouldConstructValidCrawlingProcessor05() throws InputException{	
		CrawlingProcessor cp = new CrawlingProcessor("http://hiring.axreng.com/", "four", "0");
		
		assertEquals(-1, cp.getMaxResults());
	}

}
