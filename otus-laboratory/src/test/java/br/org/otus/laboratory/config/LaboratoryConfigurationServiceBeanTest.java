package br.org.otus.laboratory.config;

import br.org.otus.laboratory.service.LaboratoryConfigurationServiceBean;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Ignore
@RunWith(PowerMockRunner.class)
@PrepareForTest({ LaboratoryConfigurationServiceBean.class })
public class LaboratoryConfigurationServiceBeanTest {

	@InjectMocks
	private LaboratoryConfigurationServiceBean laboratoryConfigurationServiceBean;

	@Mock
	private LaboratoryConfiguration tubeConfiguration;

	@Mock
	private GroupDefinition groupDefinition;

	@Mock
	private CodeDefinition codeDefinition;

	@Before
	public void setup(){
		Group defaultGroup = new Group("defaultGroup", new ArrayList<>(Arrays.asList("EDTA", "GEL")));		
		Mockito.when(tubeConfiguration.getCodeDefinitions()).thenReturn(codeDefinition);
		Mockito.when(tubeConfiguration.getGroupDefinitions()).thenReturn(groupDefinition);
		Mockito.when(groupDefinition.getDefaultGroup()).thenReturn(new ArrayList<Group>(Arrays.asList(defaultGroup)));
		

	}

	@Test
	public void should_generate_a_tube_code() {
		String result = laboratoryConfigurationServiceBean.getTubeCode(3);
		assertThat(result, equalTo("331000000"));
	}

	@Test
	public void should_generate_a_pack_of_tube_codes_of_given_size() {
		List<String> result = laboratoryConfigurationServiceBean.getCodes(7, 3);
		assertThat(result.size(), equalTo(7));
	}

	@Test
	public void should_generate_a_pack_of_tubes_of_given_size() {
		List<String> result = laboratoryConfigurationServiceBean.getDefaultTubeList();
		assertThat(result.size(), equalTo(2));
	}
}
