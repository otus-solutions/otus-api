package br.org.otus.survey.activity;

import com.google.gson.JsonObject;

public class ActivitySimplifiedFactory {
	
	public static JsonObject create() {
		JsonObject activitySimplified = new JsonObject(); 
		
		JsonObject jsonSurveyForm = new JsonObject();
		jsonSurveyForm.addProperty("objectType","SurveyForm");
		jsonSurveyForm.addProperty("surveyFormType", "FORM_INTERVIEW");	
	
		activitySimplified.addProperty("_id", "591a40807b65e4045b9011e7");
		activitySimplified.add("surveyForm", jsonSurveyForm);		
		return activitySimplified;		
	}
	

}
