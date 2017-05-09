package br.org.otus.laboratory.collect.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import br.org.otus.laboratory.collect.tube.TubeDefinition;

public class CollectGroupConfigurationTest {

	private CollectGroupConfiguration collectGroupConfiguration;
	private HashSet<TubeDefinition> tubeSet;
	private HashSet<CollectGroupDescriptor> groupDescriptors;
	private HashSet<CollectGroupDescriptor> expectedSet;

	@Before
	public void setUp() {
		tubeSet = new HashSet<TubeDefinition>();
		tubeSet.add(new TubeDefinition(1, "FLUORIDE", "POST_OVERLOAD"));

		groupDescriptors = new HashSet<>();
		groupDescriptors.add(new CollectGroupDescriptor("QC_1", "QUALITY_CONTROL", tubeSet));
		groupDescriptors.add(new CollectGroupDescriptor("QC_2", "QUALITY_CONTROL", tubeSet));
		groupDescriptors.add(new CollectGroupDescriptor("QC_3", "QUALITY_CONTROL", tubeSet));
		groupDescriptors.add(new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSet));

		collectGroupConfiguration = new CollectGroupConfiguration(groupDescriptors);

		expectedSet = new HashSet<>();
		expectedSet.add(new CollectGroupDescriptor("QC_1", "QUALITY_CONTROL", tubeSet));
		expectedSet.add(new CollectGroupDescriptor("QC_2", "QUALITY_CONTROL", tubeSet));
		expectedSet.add(new CollectGroupDescriptor("QC_3", "QUALITY_CONTROL", tubeSet));
		expectedSet.add(new CollectGroupDescriptor("DEFAULT", "DEFAULT", tubeSet));

	}

	@Test
	public void method_should_getDefaultCollectGroupDescritor() {

		String expectedGetType = expectedSet.stream().filter(group -> group.getType().equals("DEFAULT")).findFirst()
				.get().getType();

		assertEquals(expectedGetType, collectGroupConfiguration.getDefaultCollectGroupDescriptor().getType());

	}

	@Test
	public void method_should_getCollectGroupByName() {

		String expectedGetName = expectedSet.stream().filter(group -> group.getName().equals("QC_1")).findFirst().get()
				.getName();

		assertEquals(expectedGetName, collectGroupConfiguration.getCollectGroupByName("QC_1").getName());

	}

	@Test
	public void method_should_getCollectGroupsByType() {

		assertEquals(
				expectedSet.stream().filter(group -> group.getType().equals("QUALITY_CONTROL"))
						.collect(Collectors.toList()).size(),
				collectGroupConfiguration.getCollectGroupsByType("QUALITY_CONTROL").size());

	}

	
	@Test
	public void method_should_listAllGroupDescriptors() {

		assertEquals(expectedSet.size(), collectGroupConfiguration.listAllGroupDescriptors().size());
	}

	@Test
	public void method_should_listGroupNames() {

		assertTrue(expectedSet.stream().map(group -> group.getName()).collect(Collectors.toList())
				.containsAll(collectGroupConfiguration.listGroupNames()));
	}

}
