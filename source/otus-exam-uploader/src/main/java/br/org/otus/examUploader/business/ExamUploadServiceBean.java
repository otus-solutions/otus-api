package br.org.otus.examUploader.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import br.org.otus.laboratory.configuration.LaboratoryConfigurationDao;
import br.org.otus.laboratory.participant.ParticipantLaboratoryDao;
import org.apache.commons.lang3.mutable.MutableBoolean;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.examUploader.persistence.ExamDao;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamSendingLotDao;
import br.org.otus.examUploader.utils.ResponseAliquot;
import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.persistence.AliquotDao;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;

@Stateless
public class ExamUploadServiceBean implements ExamUploadService {

  private static final String ALIQUOT_NOT_FOUND_MESSAGE = "Aliquot not found";
  private static final String ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE = "Aliquot does not match exam";

  @Inject
  LaboratoryProjectService laboratoryProjectService;

  @Inject
  private ExamSendingLotDao examSendingLotDao;

  @Inject
  private ExamDao examDAO;

  @Inject
  private AliquotDao aliquotDao;

  @Inject
  private ExamResultDao examResultDAO;

  @Inject
  private ParticipantLaboratoryDao participantLaboratoryDao;

  @Inject
  private LaboratoryConfigurationDao laboratoryConfigurationDao;

  @Override
  public String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException {
    ExamSendingLot examSendingLot = examUploadDTO.getExamSendingLot();
    List<Exam> exams = examUploadDTO.getExams();
    List<ExamResult> allResults = new ArrayList<>();

    for (Exam exam : exams) {
      allResults.addAll(exam.getExamResults());
    }
    examSendingLot.setResultsQuantity(allResults.size());

    validateExamResultLot(allResults);
    validateExamResults(allResults, examSendingLot.isForcedSave());

    examSendingLot.setOperator(userEmail);
    ObjectId lotId = examSendingLotDao.insert(examSendingLot);

    for (Exam exam : exams) {
      exam.setExamSendingLotId(lotId);
      ObjectId examId = examDAO.insert(exam);
      for (ExamResult result : exam.getExamResults()) {
        result.setExamSendingLotId(lotId);
        result.setExamId(examId);
      }
    }

    examResultDAO.insertMany(allResults);

    return lotId.toString();
  }


  @Override
  public List<ExamSendingLot> list() {
    return examSendingLotDao.getAll();
  }

  @Override
  public ExamSendingLot getByID(String id) throws DataNotFoundException {
    return examSendingLotDao.getById(id);
  }

  @Override
  public void delete(String id) throws DataNotFoundException {
    examResultDAO.deleteByExamSendingLotId(id);
    examSendingLotDao.deleteById(id);
  }

  @Override
  public List<Exam> getAllByExamSendingLotId(ObjectId id) throws DataNotFoundException {
    return examResultDAO.getByExamSendingLotId(id);
  }

  /**
   * Throws error if aliquot is not a registered on the system or if result name
   * is not in possible exams array for the specified aliquot
   *
   * @param examResults
   * @param forcedSave
   * @throws DataNotFoundException
   * @throws ValidationException
   */
  @Override
  public void validateExamResults(List<ExamResult> examResults, Boolean forcedSave) throws ValidationException, DataNotFoundException {
    LaboratoryConfiguration laboratoryConfiguration = laboratoryConfigurationDao.find();
    Integer tubeToken = laboratoryConfiguration.getCodeConfiguration().getTubeToken();
    List<ExamResult> aliquotExamResults = new ArrayList<>();
    List<ExamResult> tubeExamResults = new ArrayList<>();
    List<String> tubeCodes = new ArrayList<>();

    for (ExamResult examResult : examResults) {
      String materialCode = examResult.getCode();
      char[] ch = new char[1];
      materialCode.getChars(2,3,ch,0);
      String materialToken = new String(ch);
      if (materialToken.equals(tubeToken.toString())){
        tubeCodes.add(materialCode);
        tubeExamResults.add(examResult);
      } else {
        aliquotExamResults.add(examResult);
      }
    }

    ArrayList<ResponseAliquot> invalid = new ArrayList<>();
    MutableBoolean materialNotFound = new MutableBoolean(false);
    MutableBoolean materialDoesNotMatchExam = new MutableBoolean(false);;

    validateAliquotExamResults(aliquotExamResults, invalid, materialNotFound, materialDoesNotMatchExam);

    if (materialDoesNotMatchExam.getValue()) {
      throw new ValidationException(new Throwable(ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE), invalid);
    } else if (materialNotFound.getValue() && !forcedSave) {
      throw new ValidationException(new Throwable(ALIQUOT_NOT_FOUND_MESSAGE), invalid);
    }
  }

  private void validateAliquotExamResults(List<ExamResult> aliquotExamResults, ArrayList<ResponseAliquot> invalid, MutableBoolean materialNotFound, MutableBoolean materialDoesNotMatchExam) throws DataNotFoundException {
    List<Aliquot> allAliquots = aliquotDao.getAliquots();
    HashMap<String, Aliquot> hmap = new HashMap<>();

    for (Aliquot aliquot : allAliquots) {
      hmap.putIfAbsent(aliquot.getCode(), aliquot);
    }

    AliquotExamCorrelation aliquotExamCorrelation = laboratoryProjectService.getAliquotExamCorrelation();
    for (ExamResult examResult : aliquotExamResults) {
      examResult.setIsValid(true);
      String aliquotCode = examResult.getCode();
      Aliquot found = hmap.get(aliquotCode);
      if (found == null) {
        addInvalidResult(invalid, aliquotCode, ALIQUOT_NOT_FOUND_MESSAGE, new ArrayList(), examResult);
        materialNotFound.setValue(true);
        examResult.setIsValid(false);
      } else {
        ArrayList possibleExams = (ArrayList) aliquotExamCorrelation.getHashMap().get(found.getName());
        examResult.setRecruitmentNumber(found.getRecruitmentNumber());
        examResult.setBirthdate(found.getBirthdate());
        examResult.setSex(found.getSex());
        if (!possibleExams.contains(examResult.getExamName())) {
          addInvalidResult(invalid, aliquotCode, ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE, possibleExams, examResult);
          materialDoesNotMatchExam.setValue(true);
          examResult.setIsValid(false);
        }
      }
    }
  }

  private void validateTubeExamResult(){

  }

  @Override
  public void validateExamResultLot(List<ExamResult> examResults) throws ValidationException {
    if (examResults.size() == 0) {
      throw new ValidationException(new Throwable("Empty Lot"));
    }
  }

  private void addInvalidResult(ArrayList<ResponseAliquot> invalid, String aliquotCode, String message, ArrayList possibleExams, ExamResult aSmallArray) {
    ResponseAliquot responseAliquot = new ResponseAliquot();
    responseAliquot.setAliquot(aliquotCode);
    responseAliquot.setMessage(message);
    responseAliquot.setPossibleExams(possibleExams);
    responseAliquot.setReceivedExam(aSmallArray.getExamName());
    invalid.add(responseAliquot);
  }

  @Override
  public LinkedList<ParticipantExamUploadResultExtraction> getExamResultsExtractionValues() throws DataNotFoundException {
    return examResultDAO.getExamResultsExtractionValues();
  }
}
