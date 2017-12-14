package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResult;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultDAO;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamUploadDAO;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadServiceBean implements ExamUploadService{
    @Inject
    ExamUploadDAO examUploadDAO;

    @Inject
    ExamResultDAO examResultDAO;

    @Override
    public ExamResultLot create(ExamUploadDTO examUploadDTO) {
        ExamResultLot insertResult = examUploadDAO.insert(examUploadDTO.getExamResultLot());
        ObjectId insertResultId = insertResult.getId();

        List<ExamResult> examResults = examUploadDTO.getExamResults();

        examResults.stream()
                .forEach(examResult -> {
                    examResult.setExamId(insertResultId);
                    examResult.setFieldCenter(examUploadDTO.getExamResultLot().getFieldCenter());
                });

        examResultDAO.insertMany(examResults);
        return null;
    }

    @Override
    public List<ExamResultLot> list() {
        return null;
    }

    @Override
    public ExamResultLot getByID(String id) {
        return null;
    }

    @Override
    public void delete() {

    }
}
