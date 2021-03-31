package br.org.otus.laboratory.extraction;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import br.org.otus.laboratory.configuration.collect.tube.TubeCustomMetadata;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionHeadersFactory;
import br.org.otus.laboratory.extraction.factories.LaboratoryExtractionRecordsFactory;

@RunWith(PowerMockRunner.class)
public class LaboratoryExtractionTest {

  @InjectMocks
  private LaboratoryExtraction laboratoryExtraction;
  @Mock
  private LaboratoryExtractionHeadersFactory headersFactory;
  @Mock
  private LaboratoryExtractionRecordsFactory recordsFactory;
  @Mock
  private LinkedHashSet<String> headers;
  @Mock
  private List<TubeCustomMetadata> customMetadata;

  @Before
  public void setup() {
    Whitebox.setInternalState(laboratoryExtraction, "headersFactory", headersFactory);
    Whitebox.setInternalState(laboratoryExtraction, "recordsFactory", recordsFactory);
  }

  @Test
  public void getHeaders_method_should_call_getHeaders_method() {
    laboratoryExtraction.getHeaders();
    Mockito.verify(headersFactory, Mockito.times(1)).getHeaders();
  }

  @Test
  public void getValues_method_should_call_buildResultInformation_method() throws DataNotFoundException {
    laboratoryExtraction.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).buildResultInformation();
  }

  @Test
  public void getValues_method_should_call_getValues_method() throws DataNotFoundException {
    laboratoryExtraction.getValues();
    Mockito.verify(recordsFactory, Mockito.times(1)).getRecords();
  }

}
