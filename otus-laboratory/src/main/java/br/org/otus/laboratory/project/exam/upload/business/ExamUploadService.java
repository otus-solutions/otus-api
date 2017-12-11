package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;

import java.util.List;

public interface ExamUploadService {

    public ExamResultLot create(ExamUploadDTO examUploadDTO);

    public List<ExamResultLot> list();

    public ExamResultLot getByID(String id);

    public void delete();
}