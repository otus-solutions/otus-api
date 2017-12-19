package br.org.otus.examUploader.persistence;

import br.org.otus.examUploader.ExamResult;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExamResultDao {

    public void insertMany (List<ExamResult> examResults);

    public List<ExamResult> getByExamId(ObjectId id);
}
