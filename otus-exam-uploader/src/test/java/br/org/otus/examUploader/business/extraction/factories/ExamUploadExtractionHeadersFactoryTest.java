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
    ExamUploadExtractionHeadersFactory spy = PowerMockito.spy(new ExamUploadExtractionHeadersFactory());
    PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("buildHeader");
  }

  @Test
  public void getHeaders_method_should_return_list_with_information_headers() {
    List<String> headers = examUploadExtractionHeadersFactory.getHeaders();
    boolean contains = false;

    contains = headers.contains(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.EXAM_NAME.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.RESULT.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.RELEASE_DATE.getValue());
    Assert.assertTrue(contains);

    contains = headers.contains(ExamUploadExtractionHeaders.OBSERVATIONS.getValue());
    Assert.assertTrue(contains);
  }

  @Test
  public void getHeaders_method_should_return_list_with_expected_order() {
    ExamUploadExtractionHeadersFactory factory = new ExamUploadExtractionHeadersFactory();
    List<String> headers = factory.getHeaders();

    String header = "";
    header = headers.get(0);
    Assert.assertEquals(ExamUploadExtractionHeaders.RECRUITMENT_NUMBER.getValue(), header);

    header = headers.get(1);
    Assert.assertEquals(ExamUploadExtractionHeaders.ALIQUOT_CODE.getValue(), header);

    header = headers.get(2);
    Assert.assertEquals(ExamUploadExtractionHeaders.EXAM_NAME.getValue(), header);

    header = headers.get(3);
    Assert.assertEquals(ExamUploadExtractionHeaders.RESULT.getValue(), header);

    header = headers.get(4);
    Assert.assertEquals(ExamUploadExtractionHeaders.RELEASE_DATE.getValue(), header);

    header = headers.get(5);
    Assert.assertEquals(ExamUploadExtractionHeaders.OBSERVATIONS.getValue(), header);
  }

}
