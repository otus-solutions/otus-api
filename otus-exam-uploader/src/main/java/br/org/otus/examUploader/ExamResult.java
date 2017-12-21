package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.LabObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;

import java.util.LinkedHashMap;

public class ExamResult {

    private ObjectId _id;
    private ObjectId examId;
    private String aliquotCode;
    private Long recruitmentNumber;
    private String code;
    private String name;
    private FieldCenter fieldCenter;

    private String result;
//    private LinkedHashMap<String, String> results;
    private String unity; //TODO 21/12/17: check this name
    private String observations; //TODO 21/12/17: holds observations such as reference values

    private String label;
    private String solicitationDate;
    private String collectionDate;
    private String releaseDate;


    public void setExamId(ObjectId examId) {
        this.examId = examId;
    }

    public void setRecruitmentNumber(Long recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public void setFieldCenter(FieldCenter fieldCenter) {
        this.fieldCenter = fieldCenter;
    }

    public String getAliquotCode() {
        return aliquotCode;
    }

    public Long getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public static String serialize(ExamResult examResult) {
        return getGsonBuilder().create().toJson(examResult);
    }

    public static ExamResult deserialize(String examResultJson) {
        return ExamResult.getGsonBuilder().create().fromJson(examResultJson, ExamResult.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new LabObjectIdAdapter());
        return builder;
    }
}
