package br.org.otus.laboratory.participant.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.time.LocalDateTime;

public class JsonObjectUpdateAliquotsDTOFactory implements JsonObjectFactory {

	private JsonObject json;
	private JsonArray tubes;
	private JsonObject tube;
	private JsonArray aliquots;
	private JsonObject aliquot;
	private JsonObject aliquotCollectionData;

	@Override
	public JsonObject create() {

		aliquot = new JsonObject();
		aliquot.addProperty("objectType", "Aliquot");
		aliquot.addProperty("code", 123458);
		aliquot.addProperty("name", "BIOSORO");
		aliquot.addProperty("container", "CRYOTUBE");
		aliquot.addProperty("role", "EXAM");

		aliquotCollectionData = new JsonObject();
		aliquotCollectionData.addProperty("objectType", "AliquotCollectionData");
		aliquotCollectionData.addProperty("metadata", "quebrou");
		aliquotCollectionData.addProperty("operator", "test@test.com");
		LocalDateTime time = LocalDateTime.now();
		aliquotCollectionData.addProperty("time", time.toString());

		aliquot.add("aliquotCollectionData", aliquotCollectionData);

		aliquots = new JsonArray();
		aliquots.add(aliquot);

		tube = new JsonObject();
		tube.addProperty("code", "200000");
		tube.add("aliquots", aliquots);

		tubes = new JsonArray();
		tubes.add(tube);

		json = new JsonObject();
		json.addProperty("recruitmentNumber", 12345);
		json.add("tubes", tubes);

		return json;
	}

}
