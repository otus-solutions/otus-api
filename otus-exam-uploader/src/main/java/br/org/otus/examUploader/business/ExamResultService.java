package br.org.otus.examUploader.business;

import br.org.otus.examUploader.ExamResult;

import java.util.List;

public interface ExamResultService {

    public void create(List<ExamResult> examResults);
    public List<ExamResult> getByID(String id);
    public List<ExamResult> getByAliquotCode(String aliquotCode);
}
