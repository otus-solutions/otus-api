package org.ccem.otus.model.survey.offlineActivity;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import org.bson.types.ObjectId;
import org.ccem.otus.model.survey.activity.filling.AnswerFill;
import org.ccem.otus.survey.form.SurveyForm;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.AnswerAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.List;

public class OfflineActivityCollectionsDTO {
  List<OfflineActivityCollection> offlineActivityCollections;

  public static OfflineActivityCollectionsDTO deserialize(String offlineActivityCollectionsDTO) {
    GsonBuilder builder = getGsonBuilder();
    return builder.create().fromJson(offlineActivityCollectionsDTO, OfflineActivityCollectionsDTO.class);
  }

  public static JsonElement serializeToJsonTree(OfflineActivityCollectionsDTO offlineActivityCollectionsDTO) {
    return getGsonBuilder().create().toJsonTree(offlineActivityCollectionsDTO);
  }

  public static GsonBuilder getGsonBuilder() {
    GsonBuilder builder = SurveyForm.getGsonBuilder();

    builder.registerTypeAdapter(AnswerFill.class, new AnswerAdapter());
    builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
    builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
    builder.serializeNulls();

    return builder;
  }
}
