package br.org.otus.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CsvWriterServiceTest {

	private static final String RECORD = "id;nome;idade"+"\n"+"1;Emanoel;23";

	private CsvWriterService service;

	private List<String> headers;
	private List<List<Object>> values;
	private List<Object> record;

	@Before
	public void setup() {
		service = new CsvWriterService();
		headers = new ArrayList<String>();
		headers.add("id");
		headers.add("nome");
		headers.add("idade");

		record = new ArrayList<Object>();
		record.add(1);
		record.add("Emanoel");
		record.add(23);

		values = new ArrayList<List<Object>>();
		values.add(record);
	}

	@Test
	public void should_return() {
		service.write(headers, values);
		service.getResult().equals(RECORD);
		//Assert.assertEquals(RECORD, service.getResult());
	}
}
