package br.org.otus.laboratory;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.org.otus.laboratory.collect.group.CollectGroupConfiguration;
import br.org.otus.laboratory.collect.group.CollectGroupDescriptor;
import br.org.otus.laboratory.collect.tube.Tube;
import br.org.otus.laboratory.collect.tube.TubeDefinition;
import br.org.otus.laboratory.collect.tube.generator.TubeSeed;
import br.org.otus.laboratory.label.LabelPrintConfiguration;
import br.org.otus.laboratory.label.LabelReference;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LaboratoryConfigurationServiceBean.class)

public class LaboratoryConfigurationServiceBeanTest {
	
	@InjectMocks
	private LaboratoryConfigurationServiceBean laboratoryConfigurationServiceBean;
	
	@Mock
	private LaboratoryConfigurationDao laboratorioConfigurationDao;

	@Mock
	private LaboratoryConfiguration laboratoryConfiguration;
	
	@Mock
	private TubeSeed seed;

	@Before
	public void setup() {
		
		PowerMockito.when(laboratorioConfigurationDao.find()).thenReturn(laboratoryConfiguration);	
		
	}
	
		

	@Test
	public void should_to_return_get_Default_TubeSet() {
		Set<TubeDefinition> tubeSet = new HashSet<>();
		tubeSet.add(new TubeDefinition(1, "FLOURIDE", "POST OVERLOAD"));

		Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
		collectGroupDescriptor.add(new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSet));
		CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
		PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);
		
		TubeDefinition tube = new TubeDefinition(1, "FLOURIDE", "POST OVERLOAD");
		assertEquals(tube.getCount(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getCount());
		assertEquals(tube.getType(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getType());
		assertEquals(tube.getMoment(), laboratoryConfigurationServiceBean.getDefaultTubeSet().stream().findFirst().get().getMoment());
	}
	
	@Test
	public void should_to_return_get_TubeSet_By_GroupName(){
		Set<TubeDefinition> tubeSet = new HashSet<>();
		tubeSet.add(new TubeDefinition(1, "CITRATE", "NONE"));
		
		Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
		collectGroupDescriptor.add(new CollectGroupDescriptor("MG", "Center", tubeSet));
		CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
		PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);
		
		
		TubeDefinition expectedTubeSet = new TubeDefinition(1, "CITRATE", "NONE");
		assertEquals( expectedTubeSet.getCount(), laboratoryConfigurationServiceBean.getTubeSetByGroupName("MG").stream().findFirst().get().getCount());
		assertEquals( expectedTubeSet.getType(), laboratoryConfigurationServiceBean.getTubeSetByGroupName("MG").stream().findFirst().get().getType());
		assertEquals( expectedTubeSet.getMoment(), laboratoryConfigurationServiceBean.getTubeSetByGroupName("MG").stream().findFirst().get().getMoment());		
		
	}
	
	@Test
	public void should_to_return_list_void_case_not_find_GroupName(){
		Set<TubeDefinition> tubeSet = new HashSet<>();
		tubeSet.add(new TubeDefinition(1, "CITRATE", "NONE"));		
		
		Set<CollectGroupDescriptor> collectGroupDescriptor = new HashSet<>();
		collectGroupDescriptor.add(new CollectGroupDescriptor("MG", "Center", tubeSet));
		CollectGroupConfiguration collectGroupConfiguration = new CollectGroupConfiguration(collectGroupDescriptor);
		PowerMockito.when(laboratoryConfiguration.getCollectGroupConfiguration()).thenReturn(collectGroupConfiguration);		
		
		
		Set<TubeDefinition> expectedTubeSet = new HashSet<>();		
		assertEquals(expectedTubeSet , laboratoryConfigurationServiceBean.getTubeSetByGroupName("ES"));
	
	}
	
	@Test
	public void should_get_label_order_by_Name(){
		String reference = "QC_1";
		List <LabelReference> labelReference = new ArrayList<LabelReference>();
		labelReference.add(new LabelReference("DEFAULT", "GEL", "FASTING"));				
		
		HashMap<String, List<LabelReference>> orders = new HashMap<>();
		orders.put(reference, labelReference);		
		LabelPrintConfiguration labelPrintConfiguration = new LabelPrintConfiguration(orders);		
		PowerMockito.when(laboratoryConfiguration.getLabelPrintConfiguration()).thenReturn(labelPrintConfiguration);
		
		List<LabelReference> expectedLabelReference = new ArrayList<LabelReference>();
		expectedLabelReference.add(new LabelReference("DEFAULT", "GEL", "FASTING"));
		
		assertEquals(expectedLabelReference.stream().findFirst().get().getGroupName(),
				laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getGroupName());
		assertEquals(expectedLabelReference.stream().findFirst().get().getType(),
				laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getType());
		assertEquals(expectedLabelReference.stream().findFirst().get().getMoment(),
		laboratoryConfigurationServiceBean.getLabelOrderByName("QC_1").stream().findFirst().get().getMoment());		
						
	}
	
	@Test
	public void should_to_generate_tube_codes(){
		
		seed.setTubeCount(1);
		Integer startingPoint;
		
		PowerMockito.when(laboratoryConfiguration.allocNextCodeList(seed)).thenReturn(startingPoint = 2);
		
		CodeConfiguration codeConfiguration = new CodeConfiguration();
		PowerMockito.when(codeConfiguration.generateCodeList(seed, startingPoint));		
		
		
		assertEquals(2, laboratoryConfigurationServiceBean.generateCodes(seed));
		
		
		
	}
	
	
	
	

}
