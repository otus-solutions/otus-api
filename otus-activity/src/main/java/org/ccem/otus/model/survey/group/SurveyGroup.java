package org.ccem.otus.model.survey.group;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;
import org.ccem.otus.utils.ObjectIdAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class SurveyGroup {

    @SerializedName("_id")
    private ObjectId surveyGroupID;
    private String objectType = "SurveyGroup";
    private String name;
    private List<String> surveyAcronyms;

    public ObjectId getSurveyGroupID() {
        return surveyGroupID;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getName() {
        return name;
    }

    public List<String> getSurveyAcronyms() {
        return surveyAcronyms;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String serialize(SurveyGroup surveyGroup) {
        return getGsonBuilder().create().toJson(surveyGroup);
    }

    public static SurveyGroup deserialize(String surveyGroupJson) {
        return SurveyGroup.getGsonBuilder().create().fromJson(surveyGroupJson, SurveyGroup.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        builder.serializeNulls();
        return builder;
    }
}
