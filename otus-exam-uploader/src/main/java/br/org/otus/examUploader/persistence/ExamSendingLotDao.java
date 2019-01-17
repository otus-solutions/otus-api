package br.org.otus.examUploader.persistence;

import java.util.List;

import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.examUploader.ExamSendingLot;

public interface ExamSendingLotDao {

  ObjectId insert(ExamSendingLot examSendingLot);

  List<ExamSendingLot> getAll();

  ExamSendingLot getById(String id) throws DataNotFoundException;

  void deleteById(String id) throws DataNotFoundException;
}
