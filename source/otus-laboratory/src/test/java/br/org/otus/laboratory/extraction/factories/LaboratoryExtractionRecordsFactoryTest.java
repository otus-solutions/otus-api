package br.org.otus.laboratory.extraction.factories;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.reflect.Whitebox;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;
import br.org.otus.laboratory.extraction.model.LaboratoryResultExtraction;

public class LaboratoryExtractionRecordsFactoryTest {

  private static final Long RECRUITMENT_NUMBER = 1015533L;
  private static final Integer UNATTACHED_IDENTIFICATION = 1;
  private static final String TUBE_CODE = "361005197";
  private static final Integer TUBE_QUALITY_CONTROL = 1;
  private static final String TUBE_TYPE = "GEL";
  private static final String TUBE_MOMENT = "FASTING";
  private static final String TUBE_COLLECTION_DATE = "2018-03-05T15:25:26.770Z";
  private static final String TUBE_RESPONSIBLE = "diogo.rosas.ferreira@gmail.com";
  private static final String ALIQUOT_CODE = "363210007";
  private static final String ALIQUOT_NAME = "BIOCHEMICAL_SERUM";
  private static final String ALIQUOT_CONTAINER = "CRYOTUBE";
  private static final String ALIQUOT_PROCESSING_DATE = "2018-05-22T18:54:09.189Z";
  private static final String ALIQUOT_REGISTER_DATE = "2018-05-22T18:55:06.028Z";
  private static final String ALIQUOT_RESPONSIBLE = "diogo.rosas.ferreira@gmail.com";
  private static final String ALIQUOT_ROLE = "EXAM";
  private static final Boolean ALIQUOT_HAS_TRANSPORTATION_LOT_ID = true;
  private static final Boolean ALIQUOT_HAS_EXAM_LOT_ID = false;

  private LinkedList<LaboratoryRecordExtraction> records;
  private LaboratoryExtractionRecordsFactory laboratoryExtractionRecordsFactory;

  @Before
  public void setup() {
    this.records = new LinkedList<>();
    this.laboratoryExtractionRecordsFactory = new LaboratoryExtractionRecordsFactory(this.records);
  }

  @Test
  public void getRecords_method_should_only_return_one_line() {
    this.records.add(this.createFakeLaboratoryRecordExtraction());
    this.laboratoryExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.laboratoryExtractionRecordsFactory.getRecords();

    Assert.assertEquals(1, records.size());
  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values() {
    this.records.add(this.createFakeLaboratoryRecordExtraction());
    this.laboratoryExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.laboratoryExtractionRecordsFactory.getRecords();

    List<Object> results = records.get(0);
    Assert.assertTrue(results.contains(RECRUITMENT_NUMBER.toString()));
    Assert.assertTrue(results.contains(UNATTACHED_IDENTIFICATION.toString()));
    Assert.assertTrue(results.contains(TUBE_CODE));
    Assert.assertTrue(results.contains(TUBE_QUALITY_CONTROL.toString()));
    Assert.assertTrue(results.contains(TUBE_TYPE));
    Assert.assertTrue(results.contains(TUBE_MOMENT));
    Assert.assertTrue(results.contains(TUBE_COLLECTION_DATE));
    Assert.assertTrue(results.contains(TUBE_RESPONSIBLE));
    Assert.assertTrue(results.contains(ALIQUOT_CODE));
    Assert.assertTrue(results.contains(ALIQUOT_NAME));
    Assert.assertTrue(results.contains(ALIQUOT_CONTAINER));
    Assert.assertTrue(results.contains(ALIQUOT_PROCESSING_DATE));
    Assert.assertTrue(results.contains(ALIQUOT_REGISTER_DATE));
    Assert.assertTrue(results.contains(ALIQUOT_RESPONSIBLE));
    Assert.assertTrue(results.contains(ALIQUOT_ROLE));
    Assert.assertTrue(results.contains(ALIQUOT_HAS_TRANSPORTATION_LOT_ID.toString()));
    Assert.assertTrue(results.contains(ALIQUOT_HAS_EXAM_LOT_ID.toString()));
  }

  @Test
  public void getRecords_method_should_return_list_with_expected_values_in_order() {
    this.records.add(this.createFakeLaboratoryRecordExtraction());
    this.laboratoryExtractionRecordsFactory.buildResultInformation();
    List<List<Object>> records = this.laboratoryExtractionRecordsFactory.getRecords();

    List<Object> results = records.get(0);
    Assert.assertEquals(RECRUITMENT_NUMBER.toString(), results.get(0));
    Assert.assertEquals(UNATTACHED_IDENTIFICATION.toString(), results.get(1));
    Assert.assertEquals(TUBE_CODE, results.get(2));
    Assert.assertEquals(TUBE_QUALITY_CONTROL.toString(), results.get(3));
    Assert.assertEquals(TUBE_TYPE, results.get(4));
    Assert.assertEquals(TUBE_MOMENT, results.get(5));
    Assert.assertEquals(TUBE_COLLECTION_DATE, results.get(6));
    Assert.assertEquals(TUBE_RESPONSIBLE, results.get(7));
    Assert.assertEquals(ALIQUOT_CODE, results.get(8));
    Assert.assertEquals(ALIQUOT_NAME, results.get(9));
    Assert.assertEquals(ALIQUOT_CONTAINER, results.get(10));
    Assert.assertEquals(ALIQUOT_PROCESSING_DATE, results.get(11));
    Assert.assertEquals(ALIQUOT_REGISTER_DATE, results.get(12));
    Assert.assertEquals(ALIQUOT_RESPONSIBLE, results.get(13));
    Assert.assertEquals(ALIQUOT_ROLE, results.get(14));
    Assert.assertEquals(ALIQUOT_HAS_TRANSPORTATION_LOT_ID.toString(), results.get(15));
    Assert.assertEquals(ALIQUOT_HAS_EXAM_LOT_ID.toString(), results.get(16));
  }

  private LaboratoryRecordExtraction createFakeLaboratoryRecordExtraction() {
    LinkedList<LaboratoryResultExtraction> results = new LinkedList<>();
    LaboratoryResultExtraction result = new LaboratoryResultExtraction();

    Whitebox.setInternalState(result, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(result, "unattachedLaboratoryId", UNATTACHED_IDENTIFICATION);
    Whitebox.setInternalState(result, "tubeCode", TUBE_CODE);
    Whitebox.setInternalState(result, "tubeQualityControl", TUBE_QUALITY_CONTROL);
    Whitebox.setInternalState(result, "tubeType", TUBE_TYPE);
    Whitebox.setInternalState(result, "tubeMoment", TUBE_MOMENT);
    Whitebox.setInternalState(result, "tubeCollectionDate", TUBE_COLLECTION_DATE);
    Whitebox.setInternalState(result, "tubeResponsible", TUBE_RESPONSIBLE);
    Whitebox.setInternalState(result, "aliquotCode", ALIQUOT_CODE);
    Whitebox.setInternalState(result, "aliquotName", ALIQUOT_NAME);
    Whitebox.setInternalState(result, "aliquotContainer", ALIQUOT_CONTAINER);
    Whitebox.setInternalState(result, "aliquotProcessingDate", ALIQUOT_PROCESSING_DATE);
    Whitebox.setInternalState(result, "aliquotRegisterDate", ALIQUOT_REGISTER_DATE);
    Whitebox.setInternalState(result, "aliquotResponsible", ALIQUOT_RESPONSIBLE);
    Whitebox.setInternalState(result, "aliquotRole", ALIQUOT_ROLE);
    Whitebox.setInternalState(result, "hasTransportationLotId", ALIQUOT_HAS_TRANSPORTATION_LOT_ID);
    Whitebox.setInternalState(result, "hasExamLotId", ALIQUOT_HAS_EXAM_LOT_ID);

    results.add(result);

    LaboratoryRecordExtraction record = new LaboratoryRecordExtraction();
    Whitebox.setInternalState(record, "recruitmentNumber", RECRUITMENT_NUMBER);
    Whitebox.setInternalState(record, "results", results);

    return record;
  }

}
