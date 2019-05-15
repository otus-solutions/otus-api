package br.org.otus.laboratory.configuration.permission;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import com.google.gson.GsonBuilder;

public class LaboratoryAccessPermission {

  private ObjectId _id;
  private String objectType;
  private Boolean accessPermission;

  public static String serialize(LaboratoryAccessPermission laboratoryAccessPermission) {
    return getGsonBuilder().create().toJson(laboratoryAccessPermission);
  }

  public static LaboratoryAccessPermission deserialize(String laboratoryAccessPermission) {
    return LaboratoryAccessPermission.getGsonBuilder().create().fromJson(laboratoryAccessPermission, LaboratoryAccessPermission.class);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }

  public ObjectId getId() {
    return _id;
  }

  public Boolean getAccessPermission() {
    return accessPermission;
  }

}
