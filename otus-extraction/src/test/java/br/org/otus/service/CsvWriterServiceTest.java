package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.hamcrest.CoreMatchers;

public class CsvWriterServiceTest {

	private static final String RECORD = "id;name;age" + "\n" + "1;\"Emano;el\";23" + "\n";

	private CsvWriterService service;

	private List<String> headers;
	private List<List<Object>> values;
	private List<Object> record;

	@Before
	public void setup() {
		service = new CsvWriterService();
		headers = new ArrayList<String>();
		headers.add("id");
		headers.add("name");
		headers.add("age");

		record = new ArrayList<Object>();
		record.add(1);
		record.add("Emano;el");
		record.add(23);

		values = new ArrayList<List<Object>>();
		values.add(record);
	}

	@Test
	public void should_return_an_array_of_bytes() {
		service.write(this.headers, this.values);

		Assert.assertTrue(service.getResult() instanceof byte[]);
	}

	@Test
	public void should_return_result_containing_headers() {
		service.write(this.headers, this.values);
		String result = new String(service.getResult());

		Assert.assertThat(result, CoreMatchers.containsString("id"));
		Assert.assertThat(result, CoreMatchers.containsString("name"));
		Assert.assertThat(result, CoreMatchers.containsString("age"));
	}

	@Test
	public void should_return_result_containing_records() {
		service.write(this.headers, this.values);
		String result = new String(service.getResult());

		Assert.assertThat(result, CoreMatchers.containsString("1"));
		Assert.assertThat(result, CoreMatchers.containsString("Emano;el"));
		Assert.assertThat(result, CoreMatchers.containsString("23"));
	}

	@Test
	public void should_return_the_result_as_the_record() {
		service.write(this.headers, this.values);
		String result = new String(service.getResult());

		Assert.assertEquals(RECORD, result);
	}

}
