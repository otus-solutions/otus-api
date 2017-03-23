package br.org.otus.laboratory.collect.group;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.org.otus.laboratory.collect.tube.TubeDefinition;

public class CollectGroupDescriptorAdapter implements JsonDeserializer<CollectGroupDescriptor>, JsonSerializer<CollectGroupDescriptor> {

	@Override
	public JsonElement serialize(CollectGroupDescriptor groupDescriptor, Type typeOfSrc, JsonSerializationContext context) {
		JsonObject groupDescriptorJson = new JsonObject();
		JsonArray tubeArray = new JsonArray();

		groupDescriptorJson.addProperty("name", groupDescriptor.getName());
		groupDescriptorJson.addProperty("type", groupDescriptor.getType());

		groupDescriptor.getTubes().forEach(new Consumer<TubeDefinition>() {

			@Override
			public void accept(TubeDefinition tubeDefinition) {
				tubeArray.add(context.serialize(tubeDefinition, TubeDefinition.class));
				groupDescriptorJson.add("tubeSet", tubeArray);
			}
		});

		return groupDescriptorJson;
	}

	@Override
	public CollectGroupDescriptor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Set<TubeDefinition> tubeSet = new HashSet<>();
		JsonObject jsonObject = json.getAsJsonObject();
		JsonArray jsonArray = jsonObject.get("tubeSet").getAsJsonArray();
		String groupName = jsonObject.get("name").getAsString();
		String groupType = jsonObject.get("type").getAsString();

		for (JsonElement jsonElement : jsonArray) {
			TubeDefinition deserialized = context.deserialize(jsonElement, TubeDefinition.class);
			tubeSet.add(deserialized);
		}

		return new CollectGroupDescriptor(groupName, groupType, tubeSet);
	}

}
