package br.org.otus.laboratory.collect.tube.generator;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.ccem.otus.participant.model.Participant;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.api.mockito.PowerMockito;

import br.org.otus.laboratory.LaboratoryConfigurationService;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.tube.TubeDefinition;
import junit.framework.Assert;


@RunWith(MockitoJUnitRunner.class)
public class DefaultTubeGeneratorTest {
	
	
	
	@InjectMocks
	private DefaultTubeGenerator defaultTubeGenerator;
	@Mock
	private LaboratoryConfigurationService laboratoryConfigurationService;	
	@InjectMocks
	private Participant participant;
	
	private Set<TubeDefinition> tubeSets;
	private CollectGroupDescriptor collectGroupDescriptor;	
	private TubeSeed tubeSeed;	
	private String expectTypeTubeDefinition0;
	private String expectTypeTubeDefinition1;
	private String groupNameDefault = "DEFAULT";
	
	@Before
	public void setUp() {
		tubeSets = new HashSet<TubeDefinition>();
		tubeSets.add(new TubeDefinition(1, "FLORIDE", "POST_OVERLOAD"));
		tubeSets.add(new TubeDefinition(2, "GEL", "POST_OVERLOAD"));
		collectGroupDescriptor = new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSets);
		tubeSeed = TubeSeed.generate(participant, collectGroupDescriptor);		
		PowerMockito.when(laboratoryConfigurationService.getDefaultTubeSet()).thenReturn(tubeSets);				
	}
	
	@Test
	public void method_should_getTubeDefinitions(){
        expectTypeTubeDefinition0 = tubeSets.stream().filter(t->t.getType().equals("GEL")).findFirst().get().getType();
        assertEquals(expectTypeTubeDefinition0, defaultTubeGenerator.getTubeDefinitions(tubeSeed).get(0).getType());
        
        expectTypeTubeDefinition1 = tubeSets.stream().filter(t->t.getType().equals("FLORIDE")).findFirst().get().getType();
        assertEquals(expectTypeTubeDefinition1, defaultTubeGenerator.getTubeDefinitions(tubeSeed).get(1).getType());
        
        
	}
	
	@Test
	public void method_should_return_GROUP_NAME_DEFAULT(){
		assertEquals(groupNameDefault, defaultTubeGenerator.getTubeDefinitions(tubeSeed).stream().findAny().get().getGroup());	
		//System.out.println((defaultTubeGenerator.getTubeDefinitions(tubeSeed)).stream().findAny().get().getGroup());
	}

}
