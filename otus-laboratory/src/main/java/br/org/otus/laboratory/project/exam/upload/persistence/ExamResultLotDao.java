package br.org.otus.laboratory.project.exam.upload.persistence;

import br.org.otus.laboratory.project.exam.upload.ExamResultLot;
import org.bson.types.ObjectId;

public interface ExamResultLotDao {

    public ObjectId insert (ExamResultLot examResults);
}
