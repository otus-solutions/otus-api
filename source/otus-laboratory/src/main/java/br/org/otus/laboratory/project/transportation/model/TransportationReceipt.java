package br.org.otus.laboratory.project.transportation.model;

import br.org.otus.laboratory.project.transportation.TransportationLot;
import com.google.gson.*;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.ImmutableDateAdapter;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class TransportationReceipt {
    private String objectType;
    private double temperature;
    private List<ObjectId> transportationMetadata;

    public TransportationReceipt() {
        objectType = "TransportationReceipt";
    }

    public String getObjectType() {
        return objectType;
    }
    public double getTemperature() { return temperature; }
    public List<ObjectId> getTransportationMetadata() {
        return transportationMetadata;
    }

    public static String serialize(TransportationReceipt transportationReceipt) {
        Gson builder = getGsonBuilder().create();
        return builder.toJson(transportationReceipt);
    }

    public static TransportationReceipt deserialize(String transportationReceipt) {
        return getGsonBuilder().create().fromJson(transportationReceipt, TransportationReceipt.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {
            @Override
            public ObjectId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new ObjectId(json.getAsString());
            }
        });
        return builder;
    }
}
