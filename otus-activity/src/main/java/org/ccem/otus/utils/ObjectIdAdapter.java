package org.ccem.otus.utils;

import java.lang.reflect.Type;

import org.bson.types.ObjectId;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

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
