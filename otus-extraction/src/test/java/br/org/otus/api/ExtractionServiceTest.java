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

import br.org.otus.service.CsvWriter;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExtractionServiceBean.class)
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

  @InjectMocks
  private ExtractionServiceBean ExtractionServiceMock;

  @Mock
  private CsvWriter csvWriter;

  private Extractable extractable;

  @Before
  public void setup() throws Exception {
    extractable = new ExtractionTest();

    PowerMockito
      .whenNew(CsvWriter.class)
      .withNoArguments()
      .thenReturn(csvWriter);
  }

  @Test
  public void should_call_the_write_method_to_write_the_csv() throws DataNotFoundException {
    ExtractionServiceMock.createExtraction(extractable);

    Mockito.verify(csvWriter, Mockito.times(1) )
      .write(extractable.getHeaders(), extractable.getValues());
  }

  @Test
  public void should_call_the_method_getResult_to_find_result() throws DataNotFoundException {
    ExtractionServiceMock.createExtraction(extractable);

    Mockito.verify(csvWriter, Mockito.times(1))
      .getResult();
  }

}
