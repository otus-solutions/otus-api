package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.LabObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

public class ExamResult {

    private ObjectId _id;
    private ObjectId examId;

    private String aliquotCode;
    private String examName;
    private String resultName;
    private double value;
    private String releaseDate;
    private String observations;

    private FieldCenter fieldCenter;
    private Long recruitmentNumber;
    private Sex sex;
    private ImmutableDate birthdate;

    public void setExamId(ObjectId examId) {
        this.examId = examId;
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

    public void setRecruitmentNumber(Long recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setBirthdate(ImmutableDate birthdate) {
        this.birthdate = birthdate;
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
