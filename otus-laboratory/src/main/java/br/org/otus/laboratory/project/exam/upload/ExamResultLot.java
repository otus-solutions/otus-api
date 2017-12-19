package br.org.otus.laboratory.project.exam.upload;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;

public class ExamResultLot {

    private ObjectId id;
    private String operator;
    private String sendingDate;
    private FieldCenter fieldCenter;

    public ObjectId getId() {
        return id;
    }

    public String getOperator() {
        return operator;
    }

    public String getSendingDate() {
        return sendingDate;
    }

    public FieldCenter getFieldCenter() {
        return fieldCenter;
    }

    public static String serialize(ExamResultLot examResultLot) {
        return getGsonBuilder().create().toJson(examResultLot);
    }

    public static ExamResultLot deserialize(String examResultLotJson) {
        return ExamResultLot.getGsonBuilder().create().fromJson(examResultLotJson, ExamResultLot.class);
    }

    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder();
    }
}
