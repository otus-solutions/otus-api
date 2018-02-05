package br.org.otus.examUploader.persistence;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.ExamResult;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamResultDao {

    void insertMany (List<ExamResult> examResults);

    void deleteByExamId(String id) throws DataNotFoundException;

    List<Exam> getByExamLotId(ObjectId id) throws DataNotFoundException;
}
