package br.org.otus.examUploader.persistence;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamSendingLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamDao {

  ObjectId insert(Exam exam);

  List<ExamSendingLot> getAll();

  ExamSendingLot getById(String id) throws DataNotFoundException;

  void deleteById(String id) throws DataNotFoundException;

}
