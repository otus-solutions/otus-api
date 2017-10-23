package br.org.otus.configuration.publish;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class SurveyTemplateFactory {
	
	public static JsonObject create(){
		JsonObject customID1 = new JsonObject();
		JsonObject customID2 = new JsonObject();
		customID1.addProperty("customID", "CISEQ1");
		customID1.addProperty("objectType", "SingleSelectionQuestion");
		customID2.addProperty("customID", "CISEQ2");
		customID2.addProperty("objectType", "SingleSelectionQuestion");
		JsonArray itemContainer = new JsonArray();
		itemContainer.add(customID1);
		itemContainer.add(customID2);
		JsonObject surveyTemplate = new JsonObject();
		surveyTemplate.addProperty("objecType", "survey");
		surveyTemplate.add("itemContainer", itemContainer);
		
		return surveyTemplate;
		
		
	}

}
