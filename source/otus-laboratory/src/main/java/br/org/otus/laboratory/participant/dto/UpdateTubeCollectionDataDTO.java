package br.org.otus.laboratory.participant.dto;

import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.GsonBuilder;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class UpdateTubeCollectionDataDTO {

  private List<Tube> tubes;

  public List<Tube> getTubes() {
    return tubes;
  }

  public static String serialize(UpdateTubeCollectionDataDTO updateTubeCollectionDataDTO) {
    return UpdateTubeCollectionDataDTO.getGsonBuilder().create().toJson(updateTubeCollectionDataDTO);
  }

  public static UpdateTubeCollectionDataDTO deserialize(String updateTubeCollectionDataDTO) {
    return UpdateTubeCollectionDataDTO.getGsonBuilder().create().fromJson(updateTubeCollectionDataDTO, UpdateTubeCollectionDataDTO.class);
  }

  public static GsonBuilder getGsonBuilder() {
    return new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
  }

}
