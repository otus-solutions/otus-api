package br.org.otus.laboratory.collect.tube.generator;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.ccem.otus.participant.model.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.laboratory.LaboratoryConfigurationService;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.tube.TubeDefinition;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTubeGeneratorTest {

	@InjectMocks
	private DefaultTubeGenerator defaultTubeGenerator;
	@Mock
	private LaboratoryConfigurationService laboratoryConfigurationService;
	@InjectMocks
	private Participant participant;

	private Set<TubeDefinition> tubeDefinitions;
	private CollectGroupDescriptor collectGroupDescriptor;
	private TubeSeed tubeSeed;

	private String groupNameDefault = "DEFAULT";

	@Before
	public void setUp() {
		tubeDefinitions = new HashSet<TubeDefinition>();
		tubeDefinitions.add(new TubeDefinition(1, "FLORIDE", "POST_OVERLOAD"));
		tubeDefinitions.add(new TubeDefinition(2, "GEL", "POST_OVERLOAD"));
		collectGroupDescriptor = new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeDefinitions);
		tubeSeed = TubeSeed.generate(participant, collectGroupDescriptor);
		PowerMockito.when(laboratoryConfigurationService.getDefaultTubeSet()).thenReturn(tubeDefinitions);
	}

	@Test
	public void method_should_getTubeDefinitions() {

		String expectTypeTubeGel = tubeDefinitions.stream().filter(t -> t.getType().equals("GEL")).findFirst().get()
				.getType();
		assertEquals(expectTypeTubeGel, defaultTubeGenerator.getTubeDefinitions(tubeSeed).get(0).getType());
		String expectTypeTubeFloride = tubeDefinitions.stream().filter(t -> t.getType().equals("FLORIDE")).findFirst()
				.get().getType();
		assertEquals(expectTypeTubeFloride, defaultTubeGenerator.getTubeDefinitions(tubeSeed).get(1).getType());
	}

	@Test
	public void method_should_return_GROUP_NAME_DEFAULT() {
		assertEquals(groupNameDefault,
				defaultTubeGenerator.getTubeDefinitions(tubeSeed).stream().findAny().get().getGroup());
	}

}
