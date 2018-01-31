package br.org.otus.examUploader.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import org.bson.types.ObjectId;

import java.lang.reflect.Type;

public class ExamAdapter {

    public JsonObject removeResults(JsonObject exam) {
        exam.remove("examResults");
        return exam;
    }

}
