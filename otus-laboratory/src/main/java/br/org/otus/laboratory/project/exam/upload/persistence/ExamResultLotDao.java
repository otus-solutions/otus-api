package br.org.otus.laboratory.project.exam.upload.persistence;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamResultLotDao {

    ObjectId insert(ExamResultLot examResults);

    List<ExamResultLot> getAll();

    ExamResultLot getById(String id);

    void deleteById(String id) throws DataNotFoundException;
}
