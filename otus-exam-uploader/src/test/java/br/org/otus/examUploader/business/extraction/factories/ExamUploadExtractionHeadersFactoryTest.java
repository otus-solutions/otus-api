package br.org.otus.examUploader.business.extraction.factories;

import java.util.LinkedHashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.examUploader.business.extraction.enums.ExamUploadExtractionHeaders;

@RunWith(PowerMockRunner.class)
public class ExamUploadExtractionHeadersFactoryTest {

  @InjectMocks
  private ExamUploadExtractionHeadersFactory examUploadExtractionHeadersFactory;
  @Mock
  private LinkedHashSet<String> headers;

  @Test
  public void construction_method_should_call_buildHeader_method() throws Exception {
    ExamUploadExtractionHeadersFactory spy = PowerMockito.spy(new ExamUploadExtractionHeadersFactory(headers));
    PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("buildHeader", headers);
  }

  @Test
  public void getHeaders_method_should_return_list_with_basic_information_headers() {
    List<String> headers = examUploadExtractionHeadersFactory.getHeaders();
    boolean contains = headers.contains(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());

    Assert.assertTrue(contains);
  }

  @Test
  public void getHeaders_method_should_return_list_with_additional_headers() {
    LinkedHashSet<String> additionalHeaders = this.createAdditionalHeaders();
    ExamUploadExtractionHeadersFactory factory = new ExamUploadExtractionHeadersFactory(additionalHeaders);
    List<String> headers = factory.getHeaders();

    boolean contains = false;
    contains = headers.contains(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains("VALUE_1");
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.OBSERVATIONS.getValue());
    Assert.assertTrue(contains);
  }

  @Test
  public void getHeaders_method_should_return_list_with_expected_order() {
    LinkedHashSet<String> additionalHeaders = this.createAdditionalHeaders();
    ExamUploadExtractionHeadersFactory factory = new ExamUploadExtractionHeadersFactory(additionalHeaders);
    List<String> headers = factory.getHeaders();

    String header = "";
    header = headers.get(0);
    Assert.assertEquals(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue(), header);

    header = headers.get(1);
    Assert.assertEquals(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue(), header);

    header = headers.get(2);
    Assert.assertEquals("VALUE_1", header);

    header = headers.get(3);
    Assert.assertEquals(ExamUploadExtractionHeaders.RELEASE_DATE.getValue(), header);

    header = headers.get(4);
    Assert.assertEquals(ExamUploadExtractionHeaders.OBSERVATIONS.getValue(), header);
  }

  private LinkedHashSet<String> createAdditionalHeaders() {
    LinkedHashSet<String> headers = new LinkedHashSet<>();
    headers.add("VALUE_1");
    return headers;
  }

}
