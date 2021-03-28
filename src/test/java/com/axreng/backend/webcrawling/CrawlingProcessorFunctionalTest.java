package com.axreng.backend.webcrawling;

import static org.mockito.Mockito.mock;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

public class CrawlingProcessorFunctionalTest {

	private IOUtils ioUtils;

	@InjectMocks
	private CrawlingProcessor crawlingProcessor;

	@BeforeEach
	public void setUp() throws IOException {
		this.ioUtils = mock(IOUtils.class);
	}

	@Test
	public void urlNotPresentTest() throws IOException {
		String url = "http://hiring.axreng.com/";
		Mockito.doReturn(getLines(1)).when(this.ioUtils).getHtmlLines(Mockito.any());
		this.crawlingProcessor = new CrawlingProcessor(url, "before", -1);
		crawlingProcessor.executeCrawling();
	}

	private List<String> getLines(Integer lineSelect) {
		switch (lineSelect) {
		case 0:
			return Arrays.asList("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>", //
					"<span class=\"country\">USA</span><br /> href=\"index.html\"",
					"</p></div></div><div class=\"navfooter\"><hr /><table width=\"100%\" summary=\"Navigation footer\"> ");//
		case 1:
			return Arrays.asList(
					"<a class=\"ulink\" target=\"_top\"><span class=\"package\">OpenLDAP</span> 2.4.48</a></li><li class=\"listitem\">", //
					"	You should confirm the source of any errors in this documentation, before",
					"href=\"index8.html\"");//
		case 2:
			return Arrays.asList("http://hiring.axreng.com/index.html before");
		default:
			return new ArrayList<>();

		}

	}

}
