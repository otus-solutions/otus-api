package br.org.otus.laboratory.participant.dto;

import br.org.otus.laboratory.participant.tube.Tube;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

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
    GsonBuilder gsonBuilder =  new GsonBuilder();
    gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    gsonBuilder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    return gsonBuilder;
  }

}
