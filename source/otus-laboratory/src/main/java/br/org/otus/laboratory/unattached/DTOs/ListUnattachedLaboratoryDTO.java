package br.org.otus.laboratory.unattached.DTOs;

import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.bson.types.ObjectId;

import java.util.ArrayList;

public class ListUnattachedLaboratoryDTO {

  ArrayList<UnattachedLaboratoryResume> unattachedLaboratoryList;

  public static ListUnattachedLaboratoryDTO deserialize(String laboratoryJson) {
    GsonBuilder builder = UnattachedLaboratory.getGsonBuilder();
    return builder.create().fromJson(laboratoryJson, ListUnattachedLaboratoryDTO.class);
  }

  public static JsonElement serializeToJsonTree(ListUnattachedLaboratoryDTO listUnattachedLaboratoryDTO) {
    GsonBuilder builder = UnattachedLaboratory.getGsonBuilder();
    return builder.create().toJsonTree(listUnattachedLaboratoryDTO);
  }

  private static class UnattachedLaboratoryResume {
    private ObjectId _id;
    private String collectGroupName;
    private String fieldCenterAcronym;
    private Integer identification;
  }
}
