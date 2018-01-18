package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface ExamUploadService {

    public String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException;

    public List<ExamResultLot> list();

    public ExamResultLot getByID(String id) throws DataNotFoundException;

    public void delete(String id) throws DataNotFoundException;

    public List<ExamResult> getAllByExamId(ObjectId id) throws DataNotFoundException;

    void validateExamResults(ExamUploadDTO examUploadDTO) throws DataNotFoundException, ValidationException;
}