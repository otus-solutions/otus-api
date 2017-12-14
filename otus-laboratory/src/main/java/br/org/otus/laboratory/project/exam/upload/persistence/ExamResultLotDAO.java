package br.org.otus.laboratory.project.exam.upload.persistence;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;

import java.util.List;

public interface ExamUploadDAO {

    public ExamResultLot insert (ExamResultLot examResults);
}
