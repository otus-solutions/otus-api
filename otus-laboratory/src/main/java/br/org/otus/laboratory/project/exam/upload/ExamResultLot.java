package br.org.otus.laboratory.project.exam.upload;

import org.bson.types.ObjectId;

public class ExamResultLot {

    private ObjectId id;
    private String operator;
    private String sendingDate;
    private String fieldCenter;

    public ObjectId getId() {
        return id;
    }

    public String getOperator() {
        return operator;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public String getFieldCenter() {
        return fieldCenter;
    }
}
