package br.org.otus.laboratory.participant.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonObjecParticipantLaboratoryFactory implements JsonObjectFactory {

  private JsonObject json;

  @Override
  public JsonObject create() {

    json = new JsonObject();
    json.addProperty("recruitmentNumber", 3000000);
    json.addProperty("collectGroupName", "DEFAULT");

    JsonObject tube = new JsonObject();
    tube.addProperty("type", "GEL");
    tube.addProperty("moment", "FASTING");
    tube.addProperty("code", "200000");
    tube.addProperty("groupName", "DEFAULT");
    JsonArray aliquots = new JsonArray();
    tube.add("aliquots", aliquots);
    tube.addProperty("order", 1);

    JsonArray tubes = new JsonArray();
    tubes.add(tube);
    json.add("tubes", tubes);
    JsonArray exams = new JsonArray();
    json.add("exams", exams);

    return json;
  }
}
