package br.org.otus.laboratory.configuration.lot.receipt;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

public class MaterialReceiptCustomMetadata {
    public static final String OBJECT_TYPE = "MaterialReceiptCustomMetadata";

    @SerializedName("_id")
    private ObjectId id;
    private String objectType;
    private String value;
    private String extractionValue;

    public ObjectId getId() {
        return id;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getValue() {
        return value;
    }

    public String getExtractionValue() {
        return extractionValue;
    }

    public static String serialize(MaterialReceiptCustomMetadata materialReceiptCustomMetadata) {
        return getGsonBuilder().create().toJson(materialReceiptCustomMetadata);
    }

    public static MaterialReceiptCustomMetadata deserialize(String materialReceiptCustomMetadataJson) {
        return getGsonBuilder().create().fromJson(materialReceiptCustomMetadataJson, MaterialReceiptCustomMetadata.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        return builder;
    }
}
