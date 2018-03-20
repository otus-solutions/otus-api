package br.org.otus.examUploader.utils;

import br.org.otus.examUploader.Exam;
import br.org.otus.examUploader.Observation;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;
import java.util.List;

public class ExamAdapter {

    private ObjectId _id;
    private ObjectId examSendingLotId;
    private String objectType;
    private String name;
    private List<Observation> observations;

    public static String serialize(ExamAdapter examAdapter) {
        return getGsonBuilder().create().toJson(examAdapter);
    }

    public static ExamAdapter deserialize(String examResultLotJson) {
        return ExamAdapter.getGsonBuilder().create().fromJson(examResultLotJson, ExamAdapter.class);
    }

    private static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        return builder;
    }

}
