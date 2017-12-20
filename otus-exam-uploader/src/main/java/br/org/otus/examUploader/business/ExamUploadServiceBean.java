package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.persistence.ExamResultDao;
import br.org.otus.examUploader.persistence.ExamResultLotDao;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class ExamUploadServiceBean implements ExamUploadService{

    @Inject
    private ExamResultLotDao examResultLotDAO;

    @Inject
    private ExamResultDao examResultDAO;

    @Override
    public String create(ExamUploadDTO examUploadDTO, String userEmail) {
        ExamResultLot examResultLot = examUploadDTO.getExamResultLot();
        examResultLot.setOperator(userEmail);

        ObjectId lotId = examResultLotDAO.insert(examResultLot);

        List<ExamResult> examResults = examUploadDTO.getExamResults();

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
}
