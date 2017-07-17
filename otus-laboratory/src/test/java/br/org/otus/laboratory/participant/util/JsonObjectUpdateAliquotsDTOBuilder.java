package br.org.otus.laboratory.participant.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonObjectUpdateAliquotsDTOBuilder {

	private JsonObject updateAliquotsDTO;
	private JsonArray tubes;
	
	private JsonObjectUpdateAliquotsDTOBuilder() {
		updateAliquotsDTO = new JsonObject();
		tubes = new JsonArray();
	}
	
	public JsonObjectUpdateAliquotsDTOBuilder(long recruitmentNumber) {
		this();
		updateAliquotsDTO.addProperty("recruitmentNumber", recruitmentNumber);
	}
	
	public JsonObjectUpdateAliquotsDTOBuilder addTubeCode(String code) {
		JsonObject tube = new JsonObject();
		tube.addProperty("code", code);
		tube.add("aliquotes", aNewAliquotsArray());
		return this;
	}
	
	public JsonObjectUpdateAliquotsDTOBuilder withAliquot(String code) {
		JsonArray aliquots = tubes.getAsJsonObject().get("aliquotes").getAsJsonArray();
		
//		aliquots.add(element);
		return this;
	}

	private JsonArray aNewAliquotsArray() {
		return new JsonArray();
	}

//	public JsonObject create() {
//
//		aliquot = new JsonObject();
//		aliquot.addProperty("objectType", "Aliquot");
//		aliquot.addProperty("code", 123458);
//		aliquot.addProperty("name", "BIOSORO");
//		aliquot.addProperty("container", "CRYOTUBE");
//		aliquot.addProperty("role", "EXAM");
//
//		aliquotCollectionData = new JsonObject();
//		aliquotCollectionData.addProperty("objectType", "AliquotCollectionData");
//		aliquotCollectionData.addProperty("metadata", "quebrou");
//		aliquotCollectionData.addProperty("operator", "test@test.com");
//		LocalDateTime time = LocalDateTime.now();
//		aliquotCollectionData.addProperty("time", time.toString());
//
//		aliquot.add("aliquotCollectionData", aliquotCollectionData);
//
//		aliquots = new JsonArray();
//		aliquots.add(aliquot);
//
//		
//		tubes.add(tube);
//		json.add("tubes", tubes);
//
//
//		return json;
//	}

}
