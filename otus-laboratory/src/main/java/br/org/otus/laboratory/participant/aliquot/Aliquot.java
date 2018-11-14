package br.org.otus.laboratory.participant.aliquot;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.ccem.otus.model.FieldCenter;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Aliquot extends SimpleAliquot {

    private String tubeCode;
    private ObjectId transportationLotId;
    private ObjectId examLotId;
    private Long recruitmentNumber;
    private Sex sex;
    private FieldCenter fieldCenter;
    private ImmutableDate birthdate;

    public Aliquot(){
      super();
    }

    public Aliquot(SimpleAliquot simpleAliquot){
      super(simpleAliquot.getObjectType(), simpleAliquot.getCode(), simpleAliquot.getName(), simpleAliquot.getContainer(), simpleAliquot.getRole(), simpleAliquot.getAliquotCollectionData());
    }

  public void setParticipatData(Participant participant) {
    this.setRecruitmentNumber(participant.getRecruitmentNumber());
    this.setBirthdate(participant.getBirthdate());
    this.setFieldCenter(participant.getFieldCenter());
    this.setSex(participant.getSex());
  }

  public SimpleAliquot getSimpleAliquot() {
      return SimpleAliquot.deserialize(
        serialize(this)
      );
    }

    public Long getRecruitmentNumber() {
        return recruitmentNumber;
    }

    public void setRecruitmentNumber(Long recruitmentNumber) {
        this.recruitmentNumber = recruitmentNumber;
    }

    public ImmutableDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(ImmutableDate birthdate) {
        this.birthdate = birthdate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public FieldCenter getFieldCenter() {
        return fieldCenter;
    }

    public void setFieldCenter(FieldCenter fieldCenter) {
        this.fieldCenter = fieldCenter;
    }

    public String getTubeCode() {
        return tubeCode;
    }

    public ObjectId getTransportationLotId() {
        return transportationLotId;
    }

    public ObjectId getExamLotId() {
        return examLotId;
    }

    public void setTubeCode(String tubeCode) {
        this.tubeCode = tubeCode;
    }

    public void setTransportationLotId(ObjectId transportationLotId) {
        this.transportationLotId = transportationLotId;
    }

    public void setExamLotId(ObjectId examLotId) {
        this.examLotId = examLotId;
    }

    public static String serialize(Aliquot aliquot) {
        Gson builder = getGsonBuilder().create();
        return builder.toJson(aliquot);
    }

    public static Aliquot deserialize(String aliquot) {
        return getGsonBuilder().create().fromJson(aliquot, Aliquot.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ImmutableDate.class, new ImmutableDateAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        builder.serializeNulls();

        return builder;
    }
}
