package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResult;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultDao;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultLotDao;
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
    public String create(ExamUploadDTO examUploadDTO) {
        ObjectId lotId = examResultLotDAO.insert(examUploadDTO.getExamResultLot());

        List<ExamResult> examResults = examUploadDTO.getExamResults();

        examResults.stream()
                .forEach(examResult -> {
                    examResult.setExamId(lotId);
                    examResult.setFieldCenter(examUploadDTO.getExamResultLot().getFieldCenter());
                });

        examResultDAO.insertMany(examResults);
        return lotId.toString();
    }

    @Override
    public List<ExamResultLot> list() {
        return examResultLotDAO.getAll();
    }

    @Override
    public ExamResultLot getByID(String id) {
        return examResultLotDAO.getById(id);
    }

    @Override
    public void delete(String id) throws DataNotFoundException {
        examResultLotDAO.deleteById(id);
    }

    @Override
    public List<ExamResult> getAllByExamId(ObjectId id) {
        return examResultDAO.getByExamId(id);
    }
}
