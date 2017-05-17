package br.org.otus.laboratory.collect.tube.generator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.ccem.otus.participant.model.Participant;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import br.org.otus.laboratory.LaboratoryConfiguration;
import br.org.otus.laboratory.LaboratoryConfigurationService;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.tube.TubeDefinition;

@RunWith(MockitoJUnitRunner.class)
public class QualityControlTubeGeneratorTest {

	@InjectMocks
	private QualityControlTubeGenerator qualityControlTubeGenerator;
	@Mock
	private LaboratoryConfigurationService laboratoryConfigurationService;
	@InjectMocks
	private Participant participant;
	@InjectMocks
	LaboratoryConfiguration laboratoryConfiguration;

	private Set<TubeDefinition> tubeSets;
	private CollectGroupDescriptor collectGroupDescriptor;
	private List<TubeDefinition> tubesDefinitions;
	private TubeSeed tubeSeed;
	private Set<TubeDefinition> expectedTubeSets;

	@Before
	public void setUp() {

		tubeSets = new HashSet<TubeDefinition>();
		tubeSets.add(new TubeDefinition(4, "GEL", "POST_OVERLOAD"));
		tubeSets.add(new TubeDefinition(3, "EDTA", "FASTING"));
		collectGroupDescriptor = new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSets);
		tubeSeed = TubeSeed.generate(participant, collectGroupDescriptor);

		when(laboratoryConfigurationService.getTubeSetByGroupName(tubeSeed.getCollectGroupDescriptor().getName()))
				.thenReturn(tubeSets);
	}

	@Test
	public void method_should_call_getTubeDefinitionList() {
		qualityControlTubeGenerator.getTubeDefinitions(tubeSeed);
		verify(laboratoryConfigurationService).getTubeSetByGroupName("DEFAULT");
	}

	@Test
	public void method_should_getTubeDefinitions() {
		expectedTubeSets = new HashSet<TubeDefinition>();
		expectedTubeSets.add(new TubeDefinition(4, "GEL", "POST_OVERLOAD"));
		expectedTubeSets.add(new TubeDefinition(3, "EDTA", "FASTING"));
		List<TubeDefinition> tubeDefinitions = expectedTubeSets.stream().map(definition -> definition)
				.collect(Collectors.toList());
		String typeExpected = tubeDefinitions.stream().filter(t -> t.getType().equals("GEL")).findFirst().get()
				.getType();
		String momentExpected = tubeDefinitions.stream().filter(t -> t.getMoment().equals("POST_OVERLOAD")).findFirst()
				.get().getMoment();

		assertEquals(tubeDefinitions.size(), qualityControlTubeGenerator.getTubeDefinitions(tubeSeed).size());
		assertEquals(typeExpected, qualityControlTubeGenerator.getTubeDefinitions(tubeSeed).stream()
				.filter(t -> t.getType().equals("GEL")).findFirst().get().getType());
		assertEquals(momentExpected, qualityControlTubeGenerator.getTubeDefinitions(tubeSeed).stream()
				.filter(t -> t.getMoment().equals("POST_OVERLOAD")).findFirst().get().getMoment());
	}

}
