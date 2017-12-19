package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamUploadService {

    public String create(ExamUploadDTO examUploadDTO);

    public List<ExamResultLot> list();

    public ExamResultLot getByID(String id);

    public void delete(String id) throws DataNotFoundException;

    public List<ExamResult> getAllByExamId(ObjectId id);
}