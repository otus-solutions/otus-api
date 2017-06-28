package br.org.otus.laboratory;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.collect.tube.generator.TubeSeed;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RunWith(PowerMockRunner.class)
public class LaboratoryConfigurationTest {

	@InjectMocks
	private LaboratoryConfiguration laboratoryConfiguration;

	@Mock
	private CodeConfiguration codeConfiguration;

	@Mock
	private TubeSeed seed;

	private Integer startingPoint;
	private JsonObject json;

	@Before
	public void setup() {

		json = new JsonObject();

		JsonObject codeConfiguration = new JsonObject();
		json.add("codeConfiguration", codeConfiguration);

		JsonObject tubeDescriptor = new JsonObject();
		tubeDescriptor.addProperty("name", "EDTA");
		tubeDescriptor.addProperty("label", "EDTA");
		tubeDescriptor.addProperty("color", "#660066");

		JsonArray tubeDescriptors = new JsonArray();
		tubeDescriptors.add(tubeDescriptor);

		JsonObject tubeConfiguration = new JsonObject();
		tubeConfiguration.add("tubeDescriptors", tubeDescriptors);

		json.add("tubeConfiguration", tubeConfiguration);

		JsonObject aliquotConfiguration = new JsonObject();
		json.add("aliquotConfiguration", aliquotConfiguration);

		JsonObject collectMomentDescriptor = new JsonObject();
		tubeDescriptor.addProperty("name", "POST_OVERLOAD");
		tubeDescriptor.addProperty("label", "Pós");

		JsonArray momentDescriptors = new JsonArray();
		momentDescriptors.add(collectMomentDescriptor);

		JsonObject collectMomentConfiguration = new JsonObject();
		collectMomentConfiguration.add("momentDescriptors", momentDescriptors);

		json.add("collectMomentConfiguration", collectMomentConfiguration);

		JsonObject tubeDefinition = new JsonObject();
		tubeDefinition.addProperty("count", 1);
		tubeDefinition.addProperty("type", "FLUORIDE");
		tubeDefinition.addProperty("moment", "FASTING");

		JsonArray tubeSet = new JsonArray();
		tubeSet.add(tubeDefinition);

		JsonObject collectGroupDescriptor = new JsonObject();
		collectGroupDescriptor.addProperty("name", "QC_2");
		collectGroupDescriptor.addProperty("type", "QUALITY_CONTROL");
		collectGroupDescriptor.add("tubeSet", tubeSet);

		JsonArray groupDescriptors = new JsonArray();
		groupDescriptors.add(collectGroupDescriptor);

		JsonObject collectGroupConfiguration = new JsonObject();
		collectGroupConfiguration.add("groupDescriptors", groupDescriptors);
		json.add("collectGroupConfiguration", collectGroupConfiguration);

		JsonObject labelReference = new JsonObject();
		labelReference.addProperty("groupName", "DEFAULT");
		labelReference.addProperty("type", "GEL");
		labelReference.addProperty("moment", "FASTING");

		JsonArray QC_2 = new JsonArray();
		QC_2.add(labelReference);

		JsonObject labelPrintConfiguration = new JsonObject();
		labelPrintConfiguration.add("QC_2", QC_2);
		json.add("labelPrintConfiguration", labelPrintConfiguration);

		JsonArray metadataConfiguration = new JsonArray();
		json.add("metadataConfiguration", metadataConfiguration);
	}

	@Test
	public void method_allocNextCodeList_should_call_allocCodeAndGetStartingPoint() {
		laboratoryConfiguration.allocNextCodeList(seed);
		Mockito.verify(codeConfiguration).allocCodeAndGetStartingPoint(seed.getTubeCount());
	}

	@Test
	public void method_generateNewCodeList_should_call_generateCodeList() {

		laboratoryConfiguration.generateNewCodeList(seed, startingPoint);
		Mockito.verify(codeConfiguration).generateCodeList(seed, startingPoint);
	}

	@Test
	public void method_should_deserialize_and_serialize_jsonString() {
		LaboratoryConfiguration labDeserialize = laboratoryConfiguration.deserialize(json.toString());

		assertEquals(json.toString(), laboratoryConfiguration.serialize(labDeserialize));

	}

}
