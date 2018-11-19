package br.org.otus.laboratory.project.exam.examLot.persistence;

import br.org.otus.laboratory.project.exam.examLot.ExamLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamLotDao {

  ObjectId persist(ExamLot examsLot);

  ExamLot findByCode(String code) throws DataNotFoundException;

  ExamLot update(ExamLot examsLot) throws DataNotFoundException;

  List<ExamLot> find();

  void delete(ObjectId id) throws DataNotFoundException;

  String checkIfThereInExamLot(String aliquotCode);

  ExamLot find(ObjectId examLotId) throws DataNotFoundException;
}
