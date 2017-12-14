package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.ExamResult;

import java.util.List;

public interface ExamResultService {

    public void create(List<ExamResult> examResults);
    public List<ExamResult> getByID(String id);
    public List<ExamResult> getByAliquotCode(String aliquotCode);
}
