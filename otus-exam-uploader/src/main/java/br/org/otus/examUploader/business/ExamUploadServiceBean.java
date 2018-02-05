package br.org.otus.examUploader.business;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamDao;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
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

    @Inject
    LaboratoryProjectService laboratoryProjectService;

    @Inject
    private ExamResultLotDao examResultLotDAO;

    @Inject
    private ExamDao examDAO;

    @Inject
    private ExamResultDao examResultDAO;

    @Override
    public String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException {
        ExamLot examLot = examUploadDTO.getExamLot();
        List<Exam> exams= examUploadDTO.getExams();
        List<ExamResult> allResults = new ArrayList<>();

        int ResultsCount = 0;
        for (Exam exam: exams) {
            ResultsCount += exam.getExamResults().size();
            allResults.addAll(exam.getExamResults());
        }
        examLot.setResultsQuantity(ResultsCount);

        validateExamResultLot(allResults);
        validateExamResults(allResults);

        examLot.setOperator(userEmail);
        ObjectId lotId = examResultLotDAO.insert(examLot);

        for (Exam exam: exams) {
            exam.setExamLotId(lotId);
            ObjectId examId = examDAO.insert(exam);
            for (ExamResult result: exam.getExamResults()) {
                result.setExamLotId(lotId);
                result.setExamId(examId);
            }
        }

        examResultDAO.insertMany(allResults);

        return lotId.toString();
    }

    @Override
    public List<ExamLot> list() {
        return examResultLotDAO.getAll();
    }

    @Override
    public ExamLot getByID(String id) throws DataNotFoundException {
        return examResultLotDAO.getById(id);
    }

    @Override
    public void delete(String id) throws DataNotFoundException {
        examResultDAO.deleteByExamId(id);
        examResultLotDAO.deleteById(id);
    }

    @Override
    public List<Exam> getAllByExamLotId(ObjectId id) throws DataNotFoundException {
        return examResultDAO.getByExamLotId(id);
    }
//
    @Override
    public void validateExamResults(List<ExamResult> examResults) throws DataNotFoundException, ValidationException {
        List<WorkAliquot> allAliquots = laboratoryProjectService.getAllAliquots();
        isSubset(allAliquots, examResults);
    }

    @Override
    public void validateExamResultLot(List<ExamResult> examResults) throws ValidationException {
        if (examResults.size() == 0){
            throw new ValidationException(new Throwable("Empty Lot"));
        }
    }

    /* Throws error if smallArray is not a subset of bigArray */
    private void isSubset(List<WorkAliquot> bigArray, List<ExamResult> smallArray) throws ValidationException {
        HashMap<String, WorkAliquot> hmap = new HashMap<>();
        ArrayList<String> missing = new ArrayList<>();

        // hmap stores all the values of bigArray taking aliquotCode as key
        for (WorkAliquot aBigArray : bigArray) {
            hmap.putIfAbsent(aBigArray.getCode(), aBigArray);
        }

        // loop to check if all elements of smallArray also
        // lies in bigArray
        for (ExamResult aSmallArray : smallArray) {
            String aliquotCode = aSmallArray.getAliquotCode();
            WorkAliquot found = hmap.get(aliquotCode);
            if (found == null)
                missing.add(aliquotCode);
            else {
                aSmallArray.setRecruitmentNumber(found.getRecruitmentNumber());
                aSmallArray.setBirthdate(found.getBirthdate());
                aSmallArray.setSex(found.getSex());
            }
        }

        if (missing.size() > 0){
            throw new ValidationException(new Throwable("Aliquots not found"),
                    missing);
        }
    }
}
