package br.org.otus.examUploader.persistence;

import br.org.otus.examUploader.ExamResult;
import org.bson.types.ObjectId;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import java.util.List;

public interface ExamResultDao {

    public void insertMany (List<ExamResult> examResults);

    public void deleteByExamId(String id) throws DataNotFoundException;

    public List<ExamResult> getByExamId(ObjectId id) throws DataNotFoundException;
}
