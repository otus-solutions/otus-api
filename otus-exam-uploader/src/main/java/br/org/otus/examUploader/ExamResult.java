package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.LabObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;

public class ExamResult {

    private ObjectId _id;
    private ObjectId examId;
    private String aliquotCode;
    private Long recruitmentNumber;

    private String result;
    private String name;
    private String label;
    private String solicitationDate;
    private String collectionDate;
    private String releaseDate;
    private FieldCenter fieldCenter;

    public void setExamId(ObjectId examId) {
        this.examId = examId;
    }

    public void setRecruitmentNumber(Long recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public void setFieldCenter(FieldCenter fieldCenter) {
        this.fieldCenter = fieldCenter;
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


