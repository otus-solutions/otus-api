package br.org.otus.examUploader.business;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamDao;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamSendingLotDao;
import br.org.otus.examUploader.utils.ResponseAliquot;
import br.org.otus.laboratory.configuration.aliquot.AliquotExamCorrelation;
import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Stateless
public class ExamUploadServiceBean implements ExamUploadService{

    private static final String ALIQUOT_NOT_FOUND_MESSAGE = "Aliquot not found";
    private static final String ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE = "Aliquot does not match exam";

    @Inject
    LaboratoryProjectService laboratoryProjectService;

    @Inject
    private ExamSendingLotDao examSendingLotDao;

    @Inject
    private ExamDao examDAO;

    @Inject
    private ExamResultDao examResultDAO;

    @Override
    public String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException {
        ExamSendingLot examSendingLot = examUploadDTO.getExamSendingLot();
        List<Exam> exams= examUploadDTO.getExams();
        List<ExamResult> allResults = new ArrayList<>();

        for (Exam exam: exams) {
            allResults.addAll(exam.getExamResults());
        }
        examSendingLot.setResultsQuantity(allResults.size());

        validateExamResultLot(allResults);
        validateExamResults(allResults, examSendingLot.isForcedSave());

        examSendingLot.setOperator(userEmail);
        ObjectId lotId = examSendingLotDao.insert(examSendingLot);

        for (Exam exam: exams) {
            exam.setExamSendingLotId(lotId);
            ObjectId examId = examDAO.insert(exam);
            for (ExamResult result: exam.getExamResults()) {
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

    @Override
    public void validateExamResults(List<ExamResult> examResults, Boolean forcedSave) throws DataNotFoundException, ValidationException {
        List<WorkAliquot> allAliquots = laboratoryProjectService.getAllAliquots();
        isSubset(allAliquots, examResults, forcedSave);
    }

    @Override
    public void validateExamResultLot(List<ExamResult> examResults) throws ValidationException {
        if (examResults.size() == 0){
            throw new ValidationException(new Throwable("Empty Lot"));
        }
    }

    /* Throws error if smallArray is not a subset of bigArray */
    private void isSubset(List<WorkAliquot> bigArray, List<ExamResult> smallArray, Boolean forcedSave) throws ValidationException, DataNotFoundException {
        HashMap<String, WorkAliquot> hmap = new HashMap<>();
        ArrayList<ResponseAliquot> invalid = new ArrayList<>();
        Boolean aliquotNotFound = false;
        Boolean aliquotDoesNotMatchExam = false;

        // hmap stores all the values of bigArray taking aliquotCode as key
        for (WorkAliquot aBigArray : bigArray) {
            hmap.putIfAbsent(aBigArray.getCode(), aBigArray);
        }

        // loop to check if all elements of smallArray also
        // lies in bigArray
        AliquotExamCorrelation aliquotExamCorrelation = laboratoryProjectService.getAliquotExamCorrelation();
        for (ExamResult aSmallArray : smallArray) {
            aSmallArray.setAliquotValid(true);
            String aliquotCode = aSmallArray.getAliquotCode();
            WorkAliquot found = hmap.get(aliquotCode);
            if (found == null) {
                addInvalidResult(invalid,aliquotCode,ALIQUOT_NOT_FOUND_MESSAGE,new ArrayList(),aSmallArray);
                aliquotNotFound = true;
                aSmallArray.setAliquotValid(false);
            } else {
                ArrayList possibleExams = (ArrayList) aliquotExamCorrelation.getHashMap().get(found.getName());
                aSmallArray.setRecruitmentNumber(found.getRecruitmentNumber());
                aSmallArray.setBirthdate(found.getBirthdate());
                aSmallArray.setSex(found.getSex());
                if(!possibleExams.contains(aSmallArray.getExamName())){
                    addInvalidResult(invalid,aliquotCode,ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE,possibleExams,aSmallArray);
                    aliquotDoesNotMatchExam = true;
                    aSmallArray.setAliquotValid(false);
                }
            }
        }

        if(aliquotDoesNotMatchExam){
            throw new ValidationException(new Throwable(ALIQUOT_DOES_NOT_MATCH_EXAM_MESSAGE),
                    invalid);
        }else if(aliquotNotFound && !forcedSave){
            throw new ValidationException(new Throwable(ALIQUOT_NOT_FOUND_MESSAGE),
                    invalid);
        }

    }

    private void addInvalidResult(ArrayList<ResponseAliquot> invalid, String aliquotCode, String message, ArrayList possibleExams, ExamResult aSmallArray){
        ResponseAliquot responseAliquot = new ResponseAliquot();
        responseAliquot.setAliquot(aliquotCode);
        responseAliquot.setMessage(message);
        responseAliquot.setPossibleExams(possibleExams);
        responseAliquot.setReceivedExam(aSmallArray.getExamName());
        invalid.add(responseAliquot);
    }
}
