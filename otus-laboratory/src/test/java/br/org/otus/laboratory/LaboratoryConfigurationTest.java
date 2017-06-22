package br.org.otus.laboratory;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;

import br.org.otus.laboratory.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.collect.moment.CollectMomentConfiguration;
import br.org.otus.laboratory.collect.tube.TubeConfiguration;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.label.LabelPrintConfiguration;

@RunWith(PowerMockRunner.class)
public class LaboratoryConfigurationTest {

	@InjectMocks
	private LaboratoryConfiguration laboratoryConfiguration;

	@Mock
	private CodeConfiguration codeConfiguration;
	@Mock
	private TubeConfiguration tubeConfiguration;
	@Mock
	private CollectMomentConfiguration collectMomentConfiguration;
	@Mock
	private CollectGroupConfiguration collectGroupConfiguration;
	@Mock
	private LabelPrintConfiguration labelPrintConfiguration;

	@Mock
	private TubeSeed seed;

	private Integer startingPoint;
	
	private String jsonString;

	@Before
	public void setup() {		
		jsonString = "{" + "\"codeConfiguration\":{}," + "\"tubeConfiguration\":{"
				+ "\"tubeDescriptors\":[{" + "\"name\":\"EDTA_DNA\"" + "}]" + "}," + "\"collectMomentConfiguration\":{"
				+ "\"momentDescriptors\":[{" + "\"name\":\"NONE\"," + "\"label\":\"\"" + "}]" + "},"
				+ "\"collectGroupConfiguration\":{" + "\"groupDescriptors\":[{" + "\"name\":\"QC_1\","
				+ "\"type\":\"QUALITY_CONTROL\"," + "\"tubeSet\":[{" + "\"count\":2," + "\"type\":\"EDTA\","
				+ "\"moment\":\"FASTING\"" + "}]" + "}]" + "}," + "\"labelPrintConfiguration\":{" + "\"QC_2\":[{"
				+ "\"groupName\":\"DEFAULT\"," + "\"type\":\"GEL\"," + "\"moment\":\"FASTING\"" + "}]" + "}" + "}";

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
		LaboratoryConfiguration labDeserialize = laboratoryConfiguration.deserialize(jsonString);
		String expectedlaboratorySerialize = new String(jsonString);
		//expectedlaboratorySerialize = expectedlaboratorySerialize.replace("EDTA", "GEL");
		
		assertEquals(expectedlaboratorySerialize, laboratoryConfiguration.serialize(labDeserialize));

	}

}
