package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResult;
import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import br.org.otus.laboratory.project.exam.upload.ExamUploadDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExamUploadService {

    public ExamResultLot create(ExamUploadDTO examUploadDTO);

    public List<ExamResultLot> list();

    public ExamResultLot getByID(String id);

    public void delete();

    public List<ExamResult> getAllByExamId(ObjectId id);
}