package org.ccem.otus.utils;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class ObjectIdAdapter implements JsonDeserializer<ObjectId>, JsonSerializer<ObjectId> {

    @Override
    public JsonElement serialize(ObjectId objectId, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$oid", objectId.toString());
        return jsonSerializationContext.serialize(jsonObject);
    }

    @Override
    public ObjectId deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (jsonElement.isJsonObject()) {
            String asString = jsonElement.getAsJsonObject().get("$oid").getAsString();
            return new ObjectId(asString);
        } else {
            return new ObjectId(jsonElement.getAsString());
        }
    }
}
