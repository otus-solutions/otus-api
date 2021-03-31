package br.org.otus.laboratory.project.transportation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.utils.adapters.LocalDateTimeAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ReceivedMaterial {
    private String materialCode;
    private ArrayList<ObjectId> receiptMetadata;
    private String otherMetadata;
    private String receiptDate;
    private ObjectId receiveResponsible;

    public static String serialize(ReceivedMaterial receivedMaterial) {
        Gson builder = getGsonBuilder().create();
        return builder.toJson(receivedMaterial);
    }

    public static ReceivedMaterial deserialize(String receivedMaterial) {
        return getGsonBuilder().create().fromJson(receivedMaterial, ReceivedMaterial.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        return builder;
    }

    public void setReceiveResponsible(ObjectId receiveResponsible) {
        this.receiveResponsible = receiveResponsible;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getMaterialCode() {
        return materialCode;
    }
}
