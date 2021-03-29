package br.org.otus.laboratory.project.transportation;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.service.extraction.model.ActivityProgressResultExtraction;
import org.ccem.otus.utils.LongAdapter;
import org.ccem.otus.utils.ObjectIdAdapter;
import org.ccem.otus.utils.ObjectIdToStringAdapter;

import java.util.ArrayList;

public class TrailHistoryRecord {
    private String origin;
    private String destination;
    private Boolean receipted;
    private String receiveResponsible;
    private ArrayList<ObjectId> receiptMetadata;
    private String otherMetadata;
    private String sendingDate;
    private String receiptDate;
    private ObjectId lotId;

    public static String serialize(TrailHistoryRecord trailHistoryRecord) {
        return ActivityProgressResultExtraction.getGsonBuilder().create().toJson(trailHistoryRecord);
    }

    public static TrailHistoryRecord deserialize(String trailHistoryRecordJson) {
        GsonBuilder builder = ActivityProgressResultExtraction.getGsonBuilder();
        return builder.create().fromJson(trailHistoryRecordJson, TrailHistoryRecord.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdToStringAdapter());
        builder.serializeNulls();
        return builder;
    }
}
