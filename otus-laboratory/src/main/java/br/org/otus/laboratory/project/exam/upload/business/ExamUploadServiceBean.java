package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamResultDAO;
import br.org.otus.laboratory.project.exam.upload.persistence.ExamUploadDAO;

import javax.inject.Inject;
import java.util.List;

public class ExamUploadServiceBean implements ExamUploadService{
    @Inject
    ExamUploadDAO examUploadDAO;

    @Inject
    ExamResultDAO examResultDAO;

    @Override
    public ExamResultLot create(ExamUploadDTO examUploadDTO) {
        examUploadDAO.insert(examUploadDTO.getExamResultLot());
        examResultDAO.insertMany(examUploadDTO.getExamResults());
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
