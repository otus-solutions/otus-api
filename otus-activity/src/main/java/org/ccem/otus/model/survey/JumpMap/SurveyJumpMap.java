package org.ccem.otus.model.survey.JumpMap;

import com.google.gson.GsonBuilder;
import org.bson.types.ObjectId;
import org.ccem.otus.survey.template.navigation.route.RouteCondition;
import org.ccem.otus.utils.ObjectIdAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyJumpMap {
    private ObjectId surveyOid;
    private String surveyAcronym;
    private Integer surveyVersion;

    private HashMap<String,QuestionJumps> jumpMap;

    public class QuestionJumps {
        private HashMap<String,Boolean> possibleOrigins;
        private String defaultDestination;
        private ArrayList<AlternativeDestination> alternativeDestinations;
    }

    public class AlternativeDestination {
        private ArrayList<RouteCondition> routeConditions;
        private String destination;
    }

    public HashMap<String, QuestionJumps> getJumpMap() {
        return jumpMap;
    }

    public void setValidJump(String validOrigin, String destination){
        if(this.jumpMap.get(destination).possibleOrigins.get(validOrigin) != null){
            this.jumpMap.get(destination).possibleOrigins.put(validOrigin,true);
        }
    }

    public ArrayList<AlternativeDestination> getQuestionAlternativeRoutes(String questionId){
        return this.jumpMap.get(questionId).alternativeDestinations;
    }

    public void validateDefaultJump(String questionId){
        if(this.jumpMap.get(this.jumpMap.get(questionId).defaultDestination).possibleOrigins.get(questionId) != null){
            this.jumpMap.get(questionId).possibleOrigins.put(questionId,true);
        }
    }

    public void setSurveyOid(ObjectId surveyOid) {
        this.surveyOid = surveyOid;
    }

    public void setSurveyAcronym(String acronym) {
        this.surveyAcronym = acronym;
    }

    public void setSurveyVersion(Integer version) {
        this.surveyVersion = version;
    }

    public static String serialize(SurveyJumpMap surveyGroup) {
        return getGsonBuilder().create().toJson(surveyGroup);
    }

    public static SurveyJumpMap deserialize(String surveyGroupJson) {
        return SurveyJumpMap.getGsonBuilder().create().fromJson(surveyGroupJson, SurveyJumpMap.class);
    }

    public static GsonBuilder getGsonBuilder() {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ObjectId.class, new ObjectIdAdapter());
        builder.serializeNulls();
        return builder;
    }
}
