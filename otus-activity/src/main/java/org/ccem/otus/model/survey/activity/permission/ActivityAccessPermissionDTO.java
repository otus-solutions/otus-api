package org.ccem.otus.model.survey.activity.permission;

import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;
import com.google.gson.GsonBuilder;

public class ActivityAccessPermissionDTO {  

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = new GsonBuilder();
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
    return builder;
  }
}
