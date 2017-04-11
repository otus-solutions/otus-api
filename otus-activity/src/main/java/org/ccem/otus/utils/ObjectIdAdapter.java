package org.ccem.otus.utils;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class ObjectIdAdapter implements JsonSerializer<ObjectId>, JsonDeserializer<ObjectId> {

	@Override
	public JsonElement serialize(ObjectId arg0, Type arg1, JsonSerializationContext arg2) {
		return arg2.serialize(arg0.toString());
	}

	@Override
	public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		if (arg0.isJsonObject()) {
			String asString = arg0.getAsJsonObject().get("$oid").getAsString();
			return new ObjectId(asString);
		} else {
			return new ObjectId(arg0.getAsString());
		}
	}

}
