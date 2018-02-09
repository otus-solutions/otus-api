package org.ccem.otus.utils;

import com.google.gson.*;
import org.ccem.otus.enums.DataSourceMapping;
import org.ccem.otus.model.DataSource;

import java.lang.reflect.Type;

public class DataSourceAdapter implements JsonDeserializer<DataSource>, JsonSerializer<DataSource> {
    private static final String OBJECT_TYPE = "dataSource";

    public DataSourceAdapter() {
    }

    public JsonElement serialize(DataSource src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    public DataSource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive prim = (JsonPrimitive)json.getAsJsonObject().get("dataSource");
        String objectType = prim.getAsString();
        return (DataSource) context.deserialize(json, DataSourceMapping.getEnumByObjectType(objectType).getItemClass());
    }
}
