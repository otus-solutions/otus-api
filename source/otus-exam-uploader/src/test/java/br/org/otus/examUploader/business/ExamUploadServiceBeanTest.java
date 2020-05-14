package br.org.otus.examUploader.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import br.org.otus.laboratory.configuration.CodeConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamSendingLotDao;
import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;

@RunWith(PowerMockRunner.class)
public class ExamUploadServiceBeanTest {
  String EXAM_SENDING_LOT_ID = "asfafw32f23f3q";
  private static final String ALIQUOT_EXAM_CORRELATION = "{\"_id\" : \"5b2d5b936dcabba87ee60cfe\",\"objectType\" : \"AliquotExamCorrelation\", \"aliquots\" : [{\"name\" : \"a\",\"exams\" : [\"URÉIA - SANGUE\"]}]}";

  private static String EMAIL_STRING = "fulano@detal.com";

  @InjectMocks
  private ExamUploadServiceBean service;

  @Mock
  private AliquotDao aliquotDao;

  @Mock
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  @Mock
  private LaboratoryProjectService laboratoryProjectService;

  @Mock
  private ExamSendingLotDao examResultLotDAO;

  @Mock
  private ExamResultDao examResultDAO;

  @Mock
  private ExamUploadDTO examUploadDTO;

  @Mock
  private ExamResult examResult;

  @Mock
  private ParticipantLaboratoryDao participantLaboratoryDao;

  @Mock
  private LaboratoryConfiguration laboratoryConfiguration;

  @Mock
  private CodeConfiguration codeConfiguration;

  @Mock
  private ExamSendingLot examSendingLot;

  @Mock
  private Exam exam;

  @Mock
  AliquotExamCorrelation aliquotExamCorrelation;

  @Test
  public void create_should_not_persist_when_validation_fails() throws ValidationException, DataNotFoundException {
    service = PowerMockito.spy(new ExamUploadServiceBean());

    List<ExamResult> examResults = new ArrayList<>();
    examResults.add(examResult);

    List<Exam> exams = new ArrayList<>();
    exams.add(exam);

    PowerMockito.when(examUploadDTO.getExams()).thenReturn(exams);
    PowerMockito.when(examUploadDTO.getExamSendingLot()).thenReturn(examSendingLot);
    PowerMockito.when(exam.getExamResults()).thenReturn(examResults);

    Mockito.doThrow(new ValidationException()).when(service).validateExamResults(Mockito.any(), Mockito.anyBoolean());

    try {
      service.create(examUploadDTO, EMAIL_STRING);
    } catch (ValidationException ignored) {

    } finally {
      Mockito.verify(examResultLotDAO, Mockito.times(0)).insert(examSendingLot);
      Mockito.verify(examResultDAO, Mockito.times(0)).insertMany(Mockito.any());
    }

  }

  @Test
  public void list_should_call_getAll_method() throws ValidationException, DataNotFoundException {
    service.list();
    Mockito.verify(examResultLotDAO, Mockito.times(1)).getAll();
  }

  @Test
  public void getByID_should_call_getById_method() throws ValidationException, DataNotFoundException {
    service.getByID(EXAM_SENDING_LOT_ID);
    Mockito.verify(examResultLotDAO, Mockito.times(1)).getById(EXAM_SENDING_LOT_ID);
  }

  @Test
  public void delete_should_call_deleteByExamSendingLotId_and_deleteById_methods() throws ValidationException, DataNotFoundException {
    service.delete(EXAM_SENDING_LOT_ID);
    Mockito.verify(examResultDAO, Mockito.times(1)).deleteByExamSendingLotId(EXAM_SENDING_LOT_ID);
    Mockito.verify(examResultLotDAO, Mockito.times(1)).deleteById(EXAM_SENDING_LOT_ID);
  }

  @Test
  public void getAllByExamSendingLotId_should_call_getByExamSendingLotId_method() throws ValidationException, DataNotFoundException {
    ObjectId examSendingLotObjectId = new ObjectId();
    service.getAllByExamSendingLotId(examSendingLotObjectId);
    Mockito.verify(examResultDAO, Mockito.times(1)).getByExamSendingLotId(examSendingLotObjectId);

  }

  @Test
  public void delete_should_not_delete_a_lot_if_doesnt_find_associated_exam_results() throws DataNotFoundException {
    Mockito.doThrow(new DataNotFoundException()).when(examResultDAO).deleteByExamSendingLotId(Mockito.any());
    try {
      service.delete(Mockito.any());
    } catch (DataNotFoundException ignored) {

    } finally {
      Mockito.verify(examResultLotDAO, Mockito.times(0)).deleteById(Mockito.any());
    }

  }

  @Test
  public void validateExamResults_should_not_throw_ValidationException_when_resultsToVerify_is_subSet_of_allAliquots() throws DataNotFoundException, ValidationException {
    List<ExamResult> resultsToVerify = new ArrayList<>(2);
    List<String> allAliquotCode = new ArrayList<>(10);
    HashMap<String, Aliquot> hmap = new HashMap<>();

    IntStream.range(1, 2).forEach(counter -> {
      ExamResult examResult = new ExamResult();
      examResult.setCode(String.valueOf(counter).concat("123"));
      examResult.setExamName("URÉIA - SANGUE");
      allAliquotCode.add(examResult.getCode());
      resultsToVerify.add(examResult);
    });

    IntStream.range(1, 11).forEach(counter -> {
      Aliquot mock = Mockito.mock(Aliquot.class);
      Mockito.when(mock.getName()).thenReturn(String.valueOf("a"));
      Mockito.when(mock.getCode()).thenReturn(String.valueOf(counter).concat("123"));
      hmap.putIfAbsent(mock.getCode(), mock);
    });



    aliquotExamCorrelation = AliquotExamCorrelation.deserialize(ALIQUOT_EXAM_CORRELATION);

    PowerMockito.when(aliquotDao.getExamAliquotsHashMap(allAliquotCode)).thenReturn(hmap);
    PowerMockito.when(laboratoryProjectService.getAliquotExamCorrelation()).thenReturn(aliquotExamCorrelation);
    PowerMockito.when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
    PowerMockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    PowerMockito.when(codeConfiguration.getTubeToken()).thenReturn(1);


    service.validateExamResults(resultsToVerify, examSendingLot.isForcedSave());
  }

  @Test(expected = ValidationException.class)
  public void validateExamResults_should_throw_ValidationException_when_resultsToVerify_is_not_subSet_of_allAliquots() throws DataNotFoundException, ValidationException {
    List<ExamResult> resultsToVerify = new ArrayList<>();

    ExamResult examResult = new ExamResult();
    examResult.setCode("1234");

    resultsToVerify.add(examResult);

    PowerMockito.when(aliquotDao.getAliquots()).thenReturn(new ArrayList<>());
    PowerMockito.when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
    PowerMockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    PowerMockito.when(codeConfiguration.getTubeToken()).thenReturn(1);

    service.validateExamResults(resultsToVerify, false);

  }

  @Test
  public void validateExamResults_should_not_throw_ValidationException_when_resultsToVerify_is_not_subSet_of_allAliquots_and_forcedSaved_is_true() throws DataNotFoundException, ValidationException {
    List<ExamResult> resultsToVerify = new ArrayList<>();

    ExamResult examResult = new ExamResult();
    examResult.setCode("1234");

    resultsToVerify.add(examResult);

    PowerMockito.when(aliquotDao.getAliquots()).thenReturn(new ArrayList<>());
    PowerMockito.when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
    PowerMockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    PowerMockito.when(codeConfiguration.getTubeToken()).thenReturn(1);

    service.validateExamResults(resultsToVerify, true);
  }

  @Test(expected = ValidationException.class)
  public void validateExamLot_should_throw_ValidationException_when_examResults_list_size_is_zero() throws ValidationException {
    ArrayList<ExamResult> examResults = new ArrayList<>();
    List<Exam> exams = new ArrayList<>();
    exams.add(exam);

    PowerMockito.when(examUploadDTO.getExams()).thenReturn(exams);
    PowerMockito.when(exam.getExamResults()).thenReturn(examResults);

    service.validateExamResultLot(examResults);
  }

  @Test(expected = ValidationException.class)
  public void validateExamResults_should_throw_ValidationException_when_aliquot_can_not_do_requested_exam() throws DataNotFoundException, ValidationException {
    List<ExamResult> resultsToVerify = new ArrayList<>(2);
    List<Aliquot> allAliquots = new ArrayList<>(10);

    IntStream.range(1, 2).forEach(counter -> {
      ExamResult examResult = new ExamResult();
      examResult.setExamName("URÉIA - SANGUE CEENTRAL");
      examResult.setCode(String.valueOf(counter).concat("1234"));
      resultsToVerify.add(examResult);
    });

    IntStream.range(1, 10).forEach(counter -> {
      Aliquot mock = Mockito.mock(Aliquot.class);
      Mockito.when(mock.getName()).thenReturn(String.valueOf("a"));
      Mockito.when(mock.getCode()).thenReturn(String.valueOf(counter).concat("1234"));
      allAliquots.add(mock);
    });
    aliquotExamCorrelation = AliquotExamCorrelation
      .deserialize("{\"_id\" : \"5b2d5b936dcabba87ee60cfe\",\"objectType\" : \"AliquotExamCorrelation\", \"aliquots\" : [{\"name\" : \"a\",\"exams\" : [\"URÉIA - SANGUE\"]}]}");

    PowerMockito.when(aliquotDao.getAliquots()).thenReturn(allAliquots);
    PowerMockito.when(laboratoryProjectService.getAliquotExamCorrelation()).thenReturn(aliquotExamCorrelation);
    PowerMockito.when(laboratoryConfigurationDao.find()).thenReturn(laboratoryConfiguration);
    PowerMockito.when(laboratoryConfiguration.getCodeConfiguration()).thenReturn(codeConfiguration);
    PowerMockito.when(codeConfiguration.getTubeToken()).thenReturn(1);

    service.validateExamResults(resultsToVerify, examSendingLot.isForcedSave());
  }

  @Test
  public void getExamResultsExtractionValues_should_call_getExamResultsExtractionValues_method() throws DataNotFoundException {
    service.getExamResultsExtractionValues();
    Mockito.verify(examResultDAO, Mockito.times(1)).getExamResultsExtractionValues();
  }

}
