package br.org.otus.examUploader.api;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamSendingLot;
import br.org.otus.examUploader.ExamUploadDTO;
import br.org.otus.examUploader.business.ExamUploadService;
import br.org.otus.examUploader.business.extraction.model.ParticipantExamUploadResultExtraction;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;

public class ExamUploadFacade {

  @Inject
  private ExamUploadService examUploadService;

  public String create(String examUploadJson, String userEmail) {
    String examSendingLotId;
    try {
      ExamUploadDTO examUploadDTO = ExamUploadDTO.deserialize(examUploadJson);
      examSendingLotId = examUploadService.create(examUploadDTO, userEmail);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (ValidationException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage(), e.getData()));
    } catch (Exception e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
    return examSendingLotId;
  }

  public List<ExamSendingLot> list() {
    return examUploadService.list();
  }

  public ExamSendingLot getById(String id) {
    try {
      return examUploadService.getByID(id);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public void deleteById(String id) {
    try {
      examUploadService.delete(id);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }
  }

  public List<Exam> listResults(String id) {
    try {
      ObjectId objectId = new ObjectId(id);
      return examUploadService.getAllByExamSendingLotId(objectId);
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(Validation.build(e.getCause().getMessage()));
    }

  }

  public LinkedList<ParticipantExamUploadResultExtraction> getExamResultsExtractionValues() {
    try {
      return examUploadService.getExamResultsExtractionValues();
    } catch (DataNotFoundException e) {
      throw new HttpResponseException(NotFound.build(e.getCause().getMessage()));
    }
  }
}
