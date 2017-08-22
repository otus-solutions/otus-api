package br.org.otus.laboratory.configuration.collect.group;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

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
