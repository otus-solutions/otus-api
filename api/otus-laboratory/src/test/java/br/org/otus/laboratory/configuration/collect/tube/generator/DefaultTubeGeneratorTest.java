package br.org.otus.laboratory.configuration.collect.tube.generator;

import br.org.otus.laboratory.configuration.LaboratoryConfigurationService;
import br.org.otus.laboratory.configuration.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;
import org.ccem.otus.participant.model.Participant;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DefaultTubeGeneratorTest {
	private static String GROUP_NAME_DEFAULT = "DEFAULT";

	@InjectMocks
	private DefaultTubeGenerator defaultTubeGenerator;
	@Mock
	private LaboratoryConfigurationService laboratoryConfigurationService;
	@InjectMocks
	private Participant participant;

	private Set<TubeDefinition> tubeDefinitions;
	private CollectGroupDescriptor collectGroupDescriptor;
	private TubeSeed tubeSeed;


	@Before
	public void setUp() {
		tubeDefinitions = new HashSet<TubeDefinition>();
		tubeDefinitions.add(new TubeDefinition(1, "GEL", "POST_OVERLOAD"));
		tubeDefinitions.add(new TubeDefinition(2, "FLORIDE", "POST_OVERLOAD"));
		collectGroupDescriptor = new CollectGroupDescriptor(GROUP_NAME_DEFAULT, GROUP_NAME_DEFAULT, tubeDefinitions);
		tubeSeed = TubeSeed.generate(participant, collectGroupDescriptor);
		PowerMockito.when(laboratoryConfigurationService.getDefaultTubeSet()).thenReturn(tubeDefinitions);
	}

	@Ignore
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
		assertEquals(GROUP_NAME_DEFAULT,
				defaultTubeGenerator.getTubeDefinitions(tubeSeed).stream().findAny().get().getGroup());
	}

}
