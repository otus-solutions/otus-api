package br.org.otus.examUploader.utils;

import com.google.gson.*;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class LabObjectIdAdapter implements JsonDeserializer<ObjectId>, JsonSerializer<ObjectId> {

    @Override
    public JsonElement serialize(ObjectId arg0, Type arg1, JsonSerializationContext arg2) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("$oid", arg0.toString());
        return arg2.serialize(jsonObject);
    }

    @Override
    public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
        if (arg0.isJsonObject()) {
            String asString = arg0.getAsJsonObject().get("$oid").getAsString();
            return new ObjectId(asString);
        } else {
            return new ObjectId(arg0.getAsString());
        }
    }
}
