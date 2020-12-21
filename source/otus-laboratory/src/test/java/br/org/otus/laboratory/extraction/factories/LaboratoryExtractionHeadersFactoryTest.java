package br.org.otus.laboratory.extraction.factories;

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

import br.org.otus.laboratory.extraction.enums.LaboratoryExtractionHeaders;

@RunWith(PowerMockRunner.class)
public class LaboratoryExtractionHeadersFactoryTest {

  @InjectMocks
  private LaboratoryExtractionHeadersFactory laboratoryExtractionHeadersFactory;
  @Mock
  private LinkedHashSet<String> headers;

  @Test
  public void construction_method_should_call_buildHeader_method() throws Exception {
    LaboratoryExtractionHeadersFactory spy = PowerMockito.spy(new LaboratoryExtractionHeadersFactory());

    PowerMockito.verifyPrivate(spy, Mockito.times(1)).invoke("buildHeader");
  }

  @Test
  public void getHeaders_method_should_return_list_with_information_headers() {
    List<String> headers = laboratoryExtractionHeadersFactory.getHeaders();

    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.RECRUITMENT_NUMBER.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.UNATTACHED_IDENTIFICATION.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_CODE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_QUALITY_CONTROL.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_TYPE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_MOMENT.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_COLLECTION_DATE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.TUBE_RESPONSIBLE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_CODE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_NAME.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_CONTAINER.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_PROCESSING_DATE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_REGISTER_DATE.getValue()));
    Assert.assertTrue(headers.contains(LaboratoryExtractionHeaders.ALIQUOT_RESPONSIBLE.getValue()));
  }

  @Test
  public void getHeaders_method_should_return_list_with_expected_order() {
    LaboratoryExtractionHeadersFactory factory = new LaboratoryExtractionHeadersFactory();
    List<String> headers = factory.getHeaders();

    Assert.assertEquals(LaboratoryExtractionHeaders.RECRUITMENT_NUMBER.getValue(), headers.get(0));
    Assert.assertEquals(LaboratoryExtractionHeaders.UNATTACHED_IDENTIFICATION.getValue(), headers.get(1));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_CODE.getValue(), headers.get(2));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_QUALITY_CONTROL.getValue(), headers.get(3));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_TYPE.getValue(), headers.get(4));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_MOMENT.getValue(), headers.get(5));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_COLLECTION_DATE.getValue(), headers.get(6));
    Assert.assertEquals(LaboratoryExtractionHeaders.TUBE_RESPONSIBLE.getValue(), headers.get(7));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_CODE.getValue(), headers.get(8));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_NAME.getValue(), headers.get(9));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_CONTAINER.getValue(), headers.get(10));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_PROCESSING_DATE.getValue(), headers.get(11));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_REGISTER_DATE.getValue(), headers.get(12));
    Assert.assertEquals(LaboratoryExtractionHeaders.ALIQUOT_RESPONSIBLE.getValue(), headers.get(13));
  }

}
