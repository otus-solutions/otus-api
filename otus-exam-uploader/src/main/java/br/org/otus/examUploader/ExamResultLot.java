package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.LabObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;

public class ExamResultLot {

    private ObjectId _id;
    private String operator;
    private String sendingDate;
    private FieldCenter fieldCenter;

    public ObjectId getId() {
        return _id;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
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

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new LabObjectIdAdapter());
        return builder;
    }
}
