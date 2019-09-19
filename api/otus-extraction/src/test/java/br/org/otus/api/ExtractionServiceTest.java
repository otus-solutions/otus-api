package br.org.otus.api;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.attachments.AttachmentsReport;
import br.org.otus.persistence.AttachmentsExtractionDao;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.service.CsvWriter;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ExtractionServiceBean.class)
public class ExtractionServiceTest {
  private static final String acronym = "ANTC";
  private static final Integer version = 1;
  private static final byte[] extractionReturn = null;

  /* utility class for the test */
  class ExtractionTest implements Extractable {

    @Override
    public List<String> getHeaders() {
      return new LinkedList<String>();
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

  @Mock
  private AttachmentsExtractionDao attachmentsExtractionDao;

  @Mock
  private AttachmentsReport attachmentsReport;

  private Extractable extractable;

  @Before
  public void setup() throws Exception {
    extractable = new ExtractionTest();

    PowerMockito.whenNew(CsvWriter.class).withNoArguments().thenReturn(csvWriter);
  }

  @Test
  public void should_call_the_write_method_to_write_the_csv() throws DataNotFoundException {
    ExtractionServiceMock.createExtraction(extractable);

    Mockito.verify(csvWriter, Mockito.times(1)).write(extractable.getHeaders(), extractable.getValues());
  }

  @Test
  public void should_call_the_method_getResult_to_find_result() throws DataNotFoundException {
    ExtractionServiceMock.createExtraction(extractable);

    Mockito.verify(csvWriter, Mockito.times(1)).getResult();
  }

  @Test
  public void getAttachmentsReport_should_call_the_method_fetchAttachmentsReport_of_attachmentsExtractionDao_and_getCsv_of_AttachmentsReport() throws DataNotFoundException {
    Mockito.when(attachmentsExtractionDao.fetchAttachmentsReport(acronym,version)).thenReturn(attachmentsReport);
    Mockito.when(attachmentsReport.getCsv()).thenReturn(extractionReturn);
    ExtractionServiceMock.getAttachmentsReport(acronym,version);
    Mockito.verify(attachmentsExtractionDao, Mockito.times(1))
            .fetchAttachmentsReport(acronym,version);
    Mockito.verify(attachmentsReport, Mockito.times(1))
            .getCsv();
  }

}
