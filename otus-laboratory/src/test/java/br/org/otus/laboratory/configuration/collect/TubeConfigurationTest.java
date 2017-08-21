package br.org.otus.laboratory.configuration.collect;

import br.org.otus.laboratory.configuration.LaboratoryConfiguration;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@Ignore
@RunWith(JUnit4.class)
public class TubeConfigurationTest {

	private LaboratoryConfiguration tubeConfiguration;

	/*
	 * @Before public void setup() { String json = "" + "{\"tubeDefinitions\": ["
	 * + "{" + "\"name\":\"GEL\"," + "\"label\":\"Gel\"," +
	 * "\"color\":\"#445522\"" + "}," + "{" + "\"name\":\"EDTA\"," +
	 * "\"label\":\"EDTA\"," + "\"color\":\"#112204\"" + "}," + "{" +
	 * "\"name\":\"HEPARINA\"," + "\"label\":\"Heparina\"," +
	 * "\"color\":\"#112205\"" + "}," + "]," + "\"codeDefinitions\": {" +
	 * "\"tube\": 1," + "\"pallet\": 2," + "\"cryotube\": 3," +
	 * "\"lastInsertion\":0}," + "\"groupDefinitions\": {" + "defaultGroup:[" +
	 * "{" + "\"name\":\"regular\"," + "\"tubes\":[\"EDTA\",\"GEL\"]" + "}], " +
	 * "qualityControlGroup:[" + "{" + "\"name\":\"CQ_1\"," +
	 * "\"tubes\":[\"EDTA\",\"HEPARINA\",\"HEPARINA\",\"URINA\",\"URINA\",\"URINA\"]"
	 * + "}," + "{" + "\"name\":\"CQ_2\"," +
	 * "\"tubes\":[\"EDTA\",\"HEPARINA\",\"HEPARINA\",\"URINA\",\"URINA\",\"URINA\"]"
	 * + "}" + "]}" + "}";
	 * 
	 * tubeConfiguration = new Gson().fromJson(json,
	 * LaboratoryConfiguration.class);
	 * 
	 * }
	 * 
	 * @Test public void should_deserialize_for_TubeDefinition() {
	 * List<TubeDefinition> tubeDef = tubeConfiguration.getTubeDefinitions();
	 * assertThat(tubeDef.get(0).getName(), equalTo("GEL")); }
	 * 
	 * @Test public void should_deserialize_for_codeDefinitions() { CodeDefinition
	 * codeDef = tubeConfiguration.getCodeDefinitions();
	 * assertThat(codeDef.getTubeCode(), equalTo(1)); }
	 * 
	 * @Test public void should_deserialize_for_groupDefinitions() {
	 * GroupDefinition groupDef = tubeConfiguration.getGroupDefinitions();
	 * assertThat(groupDef.getDefaultGroup().get(0).getName(),
	 * equalTo("regular")); }
	 */
}
