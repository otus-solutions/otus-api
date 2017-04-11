package br.org.otus.laboratory.collect.moment;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class CollectMomentConfigurationAdapter implements JsonDeserializer<CollectMomentConfiguration>, JsonSerializer<CollectMomentConfiguration> {

	@Override
	public JsonElement serialize(CollectMomentConfiguration momentConfiguration, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonObject = new JsonObject();
		JsonArray jsonArray = new JsonArray();

		for (CollectMomentDescriptor moment : momentConfiguration.getMomentDescriptors()) {
			jsonArray.add(context.serialize(moment, CollectMomentDescriptor.class));
			jsonObject.add("momentDescriptors", jsonArray);
		}

		return jsonObject;
	}

	@Override
	public CollectMomentConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Set<CollectMomentDescriptor> descriptors = new HashSet<>();
		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("momentDescriptors").getAsJsonArray();

		for (JsonElement jsonElement : jsonArray) {
			CollectMomentDescriptor deserialized = context.deserialize(jsonElement, CollectMomentDescriptor.class);
			descriptors.add(deserialized);
		}

		return new CollectMomentConfiguration(descriptors);
	}

}
