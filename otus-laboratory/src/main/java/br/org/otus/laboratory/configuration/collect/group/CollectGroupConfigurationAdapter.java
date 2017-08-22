package br.org.otus.laboratory.configuration.collect.group;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class CollectGroupConfigurationAdapter implements JsonDeserializer<CollectGroupConfiguration>, JsonSerializer<CollectGroupConfiguration> {

	@Override
	public JsonElement serialize(CollectGroupConfiguration groupConfiguration, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject jsonGroupConfiguration = new JsonObject();
		JsonArray groupDescriptorArray = new JsonArray();

		groupConfiguration.listAllGroupDescriptors().forEach(new Consumer<CollectGroupDescriptor>() {

			@Override
			public void accept(CollectGroupDescriptor groupDescriptor) {
				groupDescriptorArray.add(context.serialize(groupDescriptor, CollectGroupDescriptor.class));
				jsonGroupConfiguration.add("groupDescriptors", groupDescriptorArray);
			}
		});

		return jsonGroupConfiguration;
	}

	@Override
	public CollectGroupConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Set<CollectGroupDescriptor> descriptors = new HashSet<>();
		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("groupDescriptors").getAsJsonArray();

		for (JsonElement jsonElement : jsonArray) {
			CollectGroupDescriptor deserialized = context.deserialize(jsonElement, CollectGroupDescriptor.class);
			descriptors.add(deserialized);
		}

		return new CollectGroupConfiguration(descriptors);
	}

}
