package br.org.otus.header;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import br.org.otus.service.ExtractionHeaderService;

public class HeaderTest {

	private ExtractionHeaderService header;

	@Before
	public void setup() {
		header = new ExtractionHeaderService();
	}

	@Ignore
	@Test
	public void addHeader_method_should_add_column_in_list_the_headers() {
		String element = "element";
		header.addHeader(element);
		Assert.assertEquals(header.getHeaders().get(0), element);
	}
}
