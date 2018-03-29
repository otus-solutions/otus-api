package br.org.otus.examUploader.business;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import java.util.List;

public interface ExamUploadService {

    String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException;

    List<ExamSendingLot> list();

    ExamSendingLot getByID(String id) throws DataNotFoundException;

    void delete(String id) throws DataNotFoundException;

    List<Exam> getAllByExamSendingLotId(ObjectId id) throws DataNotFoundException;

    void validateExamResults(List<ExamResult> examResults, Boolean forcedSave) throws DataNotFoundException, ValidationException;

    void validateExamResultLot(List<ExamResult> examResults) throws ValidationException;
}