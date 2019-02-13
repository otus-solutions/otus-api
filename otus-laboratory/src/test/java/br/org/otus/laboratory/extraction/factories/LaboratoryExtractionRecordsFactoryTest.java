package br.org.otus.laboratory.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.extraction.model.LaboratoryResultExtraction;

public class LaboratoryExtractionRecordsFactoryTest {

  private List<String> headers;
  private LinkedList<LaboratoryRecordExtraction> records;
  private LaboratoryRecordExtraction record;

  private LaboratoryExtractionRecordsFactory laboratoryExtractionRecordsFactory;

  @Before
  public void setup() {
    LaboratoryExtractionHeadersFactory headersFactory = new LaboratoryExtractionHeadersFactory();
    this.headers = headersFactory.getHeaders();
    this.records = new LinkedList<>();

    this.laboratoryExtractionRecordsFactory = new LaboratoryExtractionRecordsFactory(this.records);
  }

  @Test
  public void getRecords_method_should_only_return_one_line() {
    this.laboratoryExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.laboratoryExtractionRecordsFactory.getRecords();
  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values() {

  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values_in_order() {

  }

  private LaboratoryResultExtraction createFakeLaboratoryResultExtraction() {
    LaboratoryResultExtraction result = new LaboratoryResultExtraction();
    
    Whitebox.setInternalState(result, "recruitmentNumber", rn);
    Whitebox.setInternalState(result, "tubeCode", aliquotCode);
    Whitebox.setInternalState(result, "tubeQualityControl", aliquotCode);
    Whitebox.setInternalState(result, "tubeType", aliquotCode);
    Whitebox.setInternalState(result, "tubeMoment", aliquotCode);
    Whitebox.setInternalState(result, "tubeCollectionDate", resultName);
    Whitebox.setInternalState(result, "tubeResponsible", value);
    Whitebox.setInternalState(result, "aliquotCode", releaseDate);
    Whitebox.setInternalState(result, "aliquotName", observations);
    Whitebox.setInternalState(result, "aliquotContainer", aliquotCode);
    Whitebox.setInternalState(result, "aliquotProcessingDate", resultName);
    Whitebox.setInternalState(result, "aliquotRegisterDate", value);
    Whitebox.setInternalState(result, "aliquotResponsible", releaseDate);

    return null;
  }

}
