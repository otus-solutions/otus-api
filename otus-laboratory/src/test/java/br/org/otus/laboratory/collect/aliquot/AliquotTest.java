package br.org.otus.laboratory.collect.aliquot;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.laboratory.collect.tube.Tube;
import br.org.otus.laboratory.participant.ParticipantLaboratory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RunWith(MockitoJUnitRunner.class)
public class AliquotTest {

	@Mock
	private ParticipantLaboratory participantLaboratory;

	private JsonObject jsonParticipantLaboratory;

	private void setup() {

		jsonParticipantLaboratory = new JsonObject();
		jsonParticipantLaboratory.addProperty("objectType", "");
		jsonParticipantLaboratory.addProperty("recruitmentNumber", new Long(1234567));
		jsonParticipantLaboratory.addProperty("collectGroupName", "DEFAULT");

		JsonObject tube = new JsonObject();
		tube.addProperty("objectType", "Tube");
		tube.addProperty("type", "GEL");
		tube.addProperty("moment", "FASTING");
		tube.addProperty("code", "331000660");
		tube.addProperty("groupName", "DEFAULT");

		JsonArray aliquotes = new JsonArray();

		tube.add("aliquotes", aliquotes);
		tube.addProperty("order", 1);

		JsonObject tubeCollectionData = new JsonObject();
		tubeCollectionData.addProperty("objectType", "TubeCollectionData");
		tubeCollectionData.addProperty("isCollected", true);
		tubeCollectionData.addProperty("metadata", "{}");
		tubeCollectionData.addProperty("operator", "test");
		tubeCollectionData.addProperty("time", "2017-06-22T13:28:40.180Z");

		JsonArray tubes = new JsonArray();
		tubes.add(tube);

		jsonParticipantLaboratory.add("tubes", tubes);

	}

	@Ignore
	@Test
	public void when_method_serialize_is_called_compare_serializeds_json_with_participant_laboratory() {
		List<Tube> tubes = new ArrayList<Tube>();
		
		ParticipantLaboratory laboratory = new ParticipantLaboratory(new Long(1234567), "DEFAULT", new ArrayList<Tube>());

		participantLaboratory.serialize(laboratory);
	}
}
