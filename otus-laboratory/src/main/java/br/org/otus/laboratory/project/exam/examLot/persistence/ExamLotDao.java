package br.org.otus.laboratory.project.exam.examLot.persistence;

import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.laboratory.project.aliquot.WorkAliquot;
import br.org.otus.laboratory.project.exam.examLot.ExamLot;

public interface ExamLotDao {

  void persist(ExamLot examsLot);

  ExamLot update(ExamLot examsLot) throws DataNotFoundException;

  List<ExamLot> find();

  void delete(String id) throws DataNotFoundException;

  List<WorkAliquot> getAllAliquotsInDB() throws DataNotFoundException;

  String checkIfThereInExamLot(String aliquotCode);

}