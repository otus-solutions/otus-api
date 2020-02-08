package br.org.otus.laboratory.configuration.collect.tube;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

public class TubeConfigurationAdapter implements JsonDeserializer<TubeConfiguration>, JsonSerializer<TubeConfiguration> {

  @Override
  public JsonElement serialize(TubeConfiguration tubeconfiguration, Type typeOfSrc, JsonSerializationContext context) {
    JsonObject jsonObject = new JsonObject();
    JsonArray jsonArray = new JsonArray();

    for (TubeDescriptor descriptor : tubeconfiguration.getTubeDescriptors()) {
      jsonArray.add(context.serialize(descriptor, TubeDescriptor.class));
      jsonObject.add("tubeDescriptors", jsonArray);
    }

    return jsonObject;
  }

  @Override
  public TubeConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    Set<TubeDescriptor> descriptors = new HashSet<>();
    JsonObject jsonObject = json.getAsJsonObject();
    JsonArray jsonArray = jsonObject.get("tubeDescriptors").getAsJsonArray();

    for (JsonElement jsonElement : jsonArray) {
      TubeDescriptor deserialized = context.deserialize(jsonElement, TubeDescriptor.class);
      descriptors.add(deserialized);
    }

    return new TubeConfiguration(descriptors);
  }

}
