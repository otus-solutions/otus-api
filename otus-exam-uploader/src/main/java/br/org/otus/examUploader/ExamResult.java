package br.org.otus.examUploader;

import br.org.otus.examUploader.utils.ObjectIdAdapter;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.util.List;

public class ExamResult {

    private ObjectId examSendingLotId;
    private ObjectId examId;
    private ObjectId _id;

    private String objectType;
    private String aliquotCode;
    private String examName;
    private String resultName;
    private String value;
    private Boolean aliquotValid;
    private String releaseDate;
    private List<Observation> observations;

    private FieldCenter fieldCenter;
    private Long recruitmentNumber = null;
    private Sex sex = null;
    private ImmutableDate birthdate = null;

    public void setExamId(ObjectId examId) {
        this.examId = examId;
    }

    public void setExamSendingLotId(ObjectId examId) {
        this.examSendingLotId = examId;
    }

    public void setFieldCenter(FieldCenter fieldCenter) {
        this.fieldCenter = fieldCenter;
    }

    public String getAliquotCode() {
        return aliquotCode;
    }

    //used for unit testing
    public void setAliquotCode(String aliquotCode) {
        this.aliquotCode = aliquotCode;
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

    public void setAliquotValid(boolean aliquotValid) {
        this.aliquotValid = aliquotValid;
    }

    public static String serialize(ExamResult examResult) {
        return getGsonBuilder().create().toJson(examResult);
    }

    public static ExamResult deserialize(String examResultJson) {
        return ExamResult.getGsonBuilder().create().fromJson(examResultJson, ExamResult.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
        return builder;
    }


}
