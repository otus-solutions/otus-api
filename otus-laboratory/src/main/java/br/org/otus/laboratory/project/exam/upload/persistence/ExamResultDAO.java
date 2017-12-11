package br.org.otus.laboratory.project.exam.upload.persistence;

import br.org.otus.laboratory.project.exam.upload.ExamResult;

import java.util.List;

public interface ExamResultDAO {

    public List<ExamResult> insertMany (List<ExamResult> examResults);
}
