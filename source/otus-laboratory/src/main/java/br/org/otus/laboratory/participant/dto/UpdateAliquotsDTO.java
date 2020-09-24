package br.org.otus.laboratory.participant.dto;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateAliquotsDTO {

  private long recruitmentNumber;
  private List<UpdateTubeAliquotsDTO> tubes;

  public List<UpdateTubeAliquotsDTO> getUpdateTubeAliquots() {
    return tubes;
  }

  public void setRecruitmentNumber(long recruitmentNumber) {
    this.recruitmentNumber = recruitmentNumber;
  }

  public static String serialize(UpdateAliquotsDTO updateAliquotsDTO) {
    return UpdateAliquotsDTO.getGsonBuilder().create().toJson(updateAliquotsDTO);
  }

  public long getRecruitmentNumber() {
    return recruitmentNumber;
  }

  public static UpdateAliquotsDTO deserialize(String updateAliquotsDTO) {
    return UpdateAliquotsDTO.getGsonBuilder().create().fromJson(updateAliquotsDTO, UpdateAliquotsDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return builder;
  }

}
