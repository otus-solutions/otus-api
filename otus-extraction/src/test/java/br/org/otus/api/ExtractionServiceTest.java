package br.org.otus.api;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.service.CsvWriterService;

@RunWith(MockitoJUnitRunner.class)
public class ExtractionServiceTest {

	/* utility class for the test */
	class ExtractionTest implements Extractable {

		@Override
		public LinkedHashSet<String> getHeaders() {
			return new LinkedHashSet<String>();
		}

		@Override
		public List<List<Object>> getValues() throws DataNotFoundException {
			return new ArrayList<List<Object>>();
		}
	}

	@Mock
	private CsvWriterService csvWriterServiceMock;
	@InjectMocks
	private ExtractionServiceBean ExtractionServiceMock;

	private ExtractionServiceBean extractionService;
	private Extractable extractable;

	@Before
	public void setup() throws DataNotFoundException {
		extractionService = new ExtractionServiceBean();
		extractable = new ExtractionTest();
	}

	@Test
	public void should_call_the_write_method_to_write_the_csv() throws DataNotFoundException {
		ExtractionServiceMock.createExtraction(extractable);

		Mockito.verify(csvWriterServiceMock, Mockito.times(1)).write(extractable.getHeaders(), extractable.getValues());
	}

	@Test
	public void should_call_the_method_getResult_to_find_result() throws DataNotFoundException {
		ExtractionServiceMock.createExtraction(extractable);

		Mockito.verify(csvWriterServiceMock, Mockito.times(1)).getResult();
	}

}
