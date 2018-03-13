package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.ObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;

public class ExamLot {

    private ObjectId _id;
    private String objectType;
    private String operator;
    private String fileName;
    private String realizationDate;
    private Integer resultsQuantity = 0;
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

    public String getRealizationDate() {
        return realizationDate;
    }

    public FieldCenter getFieldCenter() {
        return fieldCenter;
    }

    public void setResultsQuantity(Integer resultsQuantity) {
        this.resultsQuantity = resultsQuantity;
    }

    public Integer getResultsQuantity(){
        return resultsQuantity;
    }

    public static String serialize(ExamLot examResultLot) {
        return getGsonBuilder().create().toJson(examResultLot);
    }

    public static ExamLot deserialize(String examResultLotJson) {
        return ExamLot.getGsonBuilder().create().fromJson(examResultLotJson, ExamLot.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());

        return builder;
    }
}
