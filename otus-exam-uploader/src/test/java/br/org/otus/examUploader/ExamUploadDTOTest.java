package br.org.otus.examUploader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import java.util.LinkedHashMap;

public class ExamUploadDTOTest {

    @InjectMocks
    private ExamUploadDTO dto;

    private JsonObject json;


    @Before
    public void setup() {

            json = new JsonObject();
//            json.addProperty("id", 3000000);
            json.addProperty("operator", "fulano@gmail.com");
            json.addProperty("sendingDate", "2017-12-14T14:38:42.227Z");
            JsonArray results= new JsonArray();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("label","URÉIA...................................:");
            jsonObject.addProperty("result","28");
            results.add(jsonObject);

            jsonObject = new JsonObject();
            jsonObject.addProperty("label","");
            jsonObject.addProperty("result","17 a 49 mg/dL");
            results.add(jsonObject);

//            json.addProperty("fieldCenter", "2017-12-14T14:38:42.227Z");
//
//            JsonObject tube = new JsonObject();
//            tube.addProperty("type", "GEL");
//            tube.addProperty("moment", "FASTING");
//            tube.addProperty("code", "200000");
//            tube.addProperty("groupName", "DEFAULT");
//            tube.add("aliquotes", aliquotes);
//            tube.addProperty("order", 1);
//
//            JsonArray tubes = new JsonArray();
//            tubes.add(tube);
//            json.add("tubes", tubes);
//            JsonArray exams = new JsonArray();
//            json.add("exams", exams);
    }


    @Test
    public void serialize() {
    }

    @Test
    public void deserialize() {
        new LinkedHashMap<String, String>();

        ExamUploadDTO.deserialize(json.toString());
    }
}