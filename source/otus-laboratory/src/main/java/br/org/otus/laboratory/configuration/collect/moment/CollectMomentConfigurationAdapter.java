package br.org.otus.laboratory.configuration.collect.moment;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class CollectMomentConfigurationAdapter implements JsonDeserializer<CollectMomentConfiguration>, JsonSerializer<CollectMomentConfiguration> {

  @Override
  public JsonElement serialize(CollectMomentConfiguration momentConfiguration, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();

    for (MomentDescriptor moment : momentConfiguration.getMomentDescriptors()) {
      jsonArray.add(context.serialize(moment, MomentDescriptor.class));
      jsonObject.add("momentDescriptors", jsonArray);
    }

    return jsonObject;
  }

  @Override
  public CollectMomentConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Set<MomentDescriptor> descriptors = new HashSet<>();
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray jsonArray = jsonObject.get("momentDescriptors").getAsJsonArray();

    for (JsonElement jsonElement : jsonArray) {
      MomentDescriptor deserialized = context.deserialize(jsonElement, MomentDescriptor.class);
      descriptors.add(deserialized);
    }

    return new CollectMomentConfiguration(descriptors);
  }

}
