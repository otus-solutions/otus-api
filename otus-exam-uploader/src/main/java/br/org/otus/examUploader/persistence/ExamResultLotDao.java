package br.org.otus.examUploader.persistence;

import br.org.otus.examUploader.ExamResultLot;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamResultLotDao {

    ObjectId insert(ExamResultLot examResults);

    List<ExamResultLot> getAll();

    ExamResultLot getById(String id) throws DataNotFoundException;

    void deleteById(String id) throws DataNotFoundException;
}
