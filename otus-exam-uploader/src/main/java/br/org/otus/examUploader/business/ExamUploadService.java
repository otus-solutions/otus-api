package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamResultLot;
import br.org.otus.examUploader.ExamUploadDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface ExamUploadService {

    String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException;

    List<ExamResultLot> list();

    ExamResultLot getByID(String id) throws DataNotFoundException;

    void delete(String id) throws DataNotFoundException;

    List<ExamResult> getAllByExamId(ObjectId id) throws DataNotFoundException;

    void validateExamResults(ExamUploadDTO examUploadDTO) throws DataNotFoundException, ValidationException;

    void validateExamResultLot(ExamUploadDTO examUploadDTO) throws ValidationException;
}