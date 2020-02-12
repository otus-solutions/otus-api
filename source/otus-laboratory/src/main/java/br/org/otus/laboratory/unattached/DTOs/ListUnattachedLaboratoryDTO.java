package br.org.otus.laboratory.unattached.DTOs;

import br.org.otus.laboratory.unattached.model.UnattachedLaboratory;
import com.google.gson.GsonBuilder;
import org.ccem.otus.utils.LongAdapter;

import java.util.ArrayList;

public class ListUnattachedLaboratoryDTO {

  ArrayList<UnattachedLaboratoryResume> unattachedLaboratoryList;

  public static ListUnattachedLaboratoryDTO deserialize(String laboratoryJson) {
    GsonBuilder builder = UnattachedLaboratory.getGsonBuilder();
    builder.registerTypeAdapter(Long.class, new LongAdapter());
    return builder.create().fromJson(laboratoryJson, ListUnattachedLaboratoryDTO.class);
  }

  private static class UnattachedLaboratoryResume {
    private String _id;
    private String collectGroupName;
    private String fieldCenterAcronym;
    private Integer identification;
  }
}
