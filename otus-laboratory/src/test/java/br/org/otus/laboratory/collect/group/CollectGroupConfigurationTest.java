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
	private HashSet<TubeDefinition> tubes;
	private HashSet<CollectGroupDescriptor> groupDescriptors;
	private HashSet<CollectGroupDescriptor> expectedSet;
	private String QC1, QC2, QC3, QC, DF;

	@Before
	public void setUp() {
		tubes = new HashSet<TubeDefinition>();
		tubes.add(new TubeDefinition(1, "FLUORIDE", "POST_OVERLOAD"));

		QC1 = "QC_1";
		QC2 = "QC_2";
		QC3 = "QC_3";
		QC = "QUALITY_CONTROL";
		DF = "DEFAULT";

		groupDescriptors = new HashSet<>();
		groupDescriptors.add(new CollectGroupDescriptor(QC1, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(QC2, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(QC3, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(DF, DF, tubes));

		collectGroupConfiguration = new CollectGroupConfiguration(groupDescriptors);

		expectedSet = new HashSet<>();
		expectedSet.add(new CollectGroupDescriptor(QC1, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(QC2, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(QC3, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(DF, DF, tubes));

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
