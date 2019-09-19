package br.org.otus.examUploader.business;

import java.util.LinkedList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadRecordExtraction;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;

public interface ExamUploadService {

  String create(ExamUploadDTO examUploadDTO, String userEmail) throws DataNotFoundException, ValidationException;

  List<ExamSendingLot> list();

  ExamSendingLot getByID(String id) throws DataNotFoundException;

  void delete(String id) throws DataNotFoundException;

  List<Exam> getAllByExamSendingLotId(ObjectId id) throws DataNotFoundException;

  void validateExamResults(List<ExamResult> examResults, Boolean forcedSave) throws DataNotFoundException, ValidationException;

  void validateExamResultLot(List<ExamResult> examResults) throws ValidationException;

  LinkedList<ParticipantExamUploadResultExtraction> getExamResultsExtractionValues() throws DataNotFoundException;

}