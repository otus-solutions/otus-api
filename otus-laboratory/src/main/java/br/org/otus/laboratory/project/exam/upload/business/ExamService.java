package br.org.otus.laboratory.project.exam.upload.business;

import br.org.otus.laboratory.project.exam.upload.Exam;

import java.util.List;

public interface ExamService {

    public Exam create(Exam exam);

    public List<Exam> list();

    public Exam getByID(String id);

    public void delete();
}