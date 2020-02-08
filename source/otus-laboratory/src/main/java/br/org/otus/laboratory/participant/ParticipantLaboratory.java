package br.org.otus.laboratory.participant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.LongAdapter;

import com.google.gson.GsonBuilder;

import br.org.otus.laboratory.participant.aliquot.Aliquot;
import br.org.otus.laboratory.participant.aliquot.SimpleAliquot;
import br.org.otus.laboratory.participant.exam.Exam;
import br.org.otus.laboratory.participant.tube.Tube;

public class ParticipantLaboratory {

  private String objectType;
  private Long recruitmentNumber;
  private String collectGroupName;
  private List<Tube> tubes;
  private List<Exam> exams;

  public ParticipantLaboratory(Long recruitmentNumber, String collectGroupName, List<Tube> tubes) {
    this.objectType = "ParticipantLaboratory";
    this.recruitmentNumber = recruitmentNumber;
    this.collectGroupName = collectGroupName;
    this.tubes = tubes;
    this.exams = new ArrayList<>();
  }

  public Long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public String getObjectType() {
    return objectType;
  }

  public String getCollectGroupName() {
    return collectGroupName;
  }

  public List<Tube> getTubes() {
    return tubes;
  }

  public List<Exam> getExams() {
    return exams;
  }

  public List<SimpleAliquot> getAliquotsList() {
    ArrayList<SimpleAliquot> aliquotsList = new ArrayList<SimpleAliquot>();
    for (Tube tube : tubes) {
      aliquotsList.addAll(tube.getAliquots());
    }

    return aliquotsList;
  }

  public void setAliquots(List<Aliquot> aliquotList) throws DataNotFoundException {

    for (int i = 0; i < aliquotList.size(); i++) {
      Aliquot aliquot = aliquotList.get(i);
      Tube tube = tubes.stream()
        .filter(atube -> atube.getCode().equals(aliquot.getTubeCode()))
        .findFirst()
        .orElse(null);
      if (tube == null) {
        throw new DataNotFoundException("Tube not found");
      }
      tube.addAliquot(aliquot);
    }
  }

  public static String serialize(ParticipantLaboratory laboratory) {
    GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
    return builder.create().toJson(laboratory);
  }

  public static ParticipantLaboratory deserialize(String laboratoryJson) {
    GsonBuilder builder = ParticipantLaboratory.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(laboratoryJson, ParticipantLaboratory.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();

    return builder;
  }

}
