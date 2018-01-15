package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import br.org.otus.laboratory.project.business.LaboratoryProjectService;
import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;
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
    private ExamResultDao examResultDAO;

    @Override
    public String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException {
        validateExamResults(examUploadDTO);


        ExamResultLot examResultLot = examUploadDTO.getExamResultLot();
        List<ExamResult> examResults = examUploadDTO.getExamResults();

        examResultLot.setOperator(userEmail);

        ObjectId lotId = examResultLotDAO.insert(examResultLot);
        examResultLot.setResultsQuantity(examResults.size());

        examResults.stream()
                .forEach(examResult -> {
                    examResult.setExamId(lotId);
                    examResult.setFieldCenter(examResultLot.getFieldCenter());
                });

        examResultDAO.insertMany(examResults);
        return lotId.toString();
    }

    @Override
    public List<ExamResultLot> list() {
        return examResultLotDAO.getAll();
    }

    @Override
    public ExamResultLot getByID(String id) throws DataNotFoundException {
        return examResultLotDAO.getById(id);
    }

    @Override
    public void delete(String id) throws DataNotFoundException {
        examResultDAO.deleteByExamId(id);
        examResultLotDAO.deleteById(id);
    }

    @Override
    public List<ExamResult> getAllByExamId(ObjectId id) throws DataNotFoundException {
        return examResultDAO.getByExamId(id);
    }

    private void validateExamResults(ExamUploadDTO examUploadDTO) throws DataNotFoundException, ValidationException {
        List<TransportationAliquot> allAliquots = laboratoryProjectService.getAllAliquots();
        List<ExamResult> examResults = examUploadDTO.getExamResults();
        isSubset(allAliquots, examResults);
    }

    /* Throws error if smallArray is not a subset of bigArray */
    private void isSubset(List<TransportationAliquot> bigArray, List<ExamResult> smallArray) throws ValidationException {
        HashMap<String, TransportationAliquot> hmap = new HashMap<>();
        ArrayList<String> missing = new ArrayList<>();

        // hmap stores all the values of bigArray taking aliquotCode as key
        for (TransportationAliquot aBigArray : bigArray) {
            hmap.putIfAbsent(aBigArray.getCode(), aBigArray);
        }

        // loop to check if all elements of smallArray also
        // lies in bigArray
        for (ExamResult aSmallArray : smallArray) {
            String aliquotCode = aSmallArray.getAliquotCode();
            TransportationAliquot found = hmap.get(aliquotCode);
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
