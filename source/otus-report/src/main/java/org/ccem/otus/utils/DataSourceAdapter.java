package org.ccem.otus.utils;

import com.google.gson.*;
import org.ccem.otus.enums.DataSourceMapping;
import org.ccem.otus.model.dataSources.ReportDataSource;

import java.lang.reflect.Type;

public class DataSourceAdapter implements JsonDeserializer<ReportDataSource>, JsonSerializer<ReportDataSource> {
    private static final String OBJECT_TYPE = "dataSource";

    public DataSourceAdapter() {
    }

    public JsonElement serialize(ReportDataSource src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src);
    }

    public ReportDataSource deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonPrimitive prim = (JsonPrimitive)json.getAsJsonObject().get("dataSource");
        String objectType = prim.getAsString();
        return (ReportDataSource) context.deserialize(json, DataSourceMapping.getEnumByObjectType(objectType).getItemClass());
    }
}
