package br.org.otus.laboratory.project.exam.upload.persistence;

import br.org.otus.laboratory.project.exam.upload.ExamResult;
import org.bson.types.ObjectId;

import java.util.List;

public interface ExamResultDao {

    public void insertMany (List<ExamResult> examResults);

    public List<ExamResult> getByExamId(ObjectId id);
}
