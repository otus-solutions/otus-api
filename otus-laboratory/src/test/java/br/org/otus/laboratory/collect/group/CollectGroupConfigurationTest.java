package br.org.otus.laboratory.collect.group;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import br.org.otus.laboratory.collect.tube.TubeDefinition;

public class CollectGroupConfigurationTest {
	private static String DEFAULT = "DEFAULT";
	private static String QC = "QUALITY_CONTROL";
	private static String QC1 = "QC_1";
	private static String QC2 = "QC_2";
	private static String QC3 = "QC_3";

	private CollectGroupConfiguration collectGroupConfiguration;
	private HashSet<TubeDefinition> tubes;
	private HashSet<CollectGroupDescriptor> groupDescriptors;
	private HashSet<CollectGroupDescriptor> expectedSet;

	@Before
	public void setUp() {
		tubes = new HashSet<TubeDefinition>();
		tubes.add(new TubeDefinition(1, "FLUORIDE", "POST_OVERLOAD"));

		groupDescriptors = new HashSet<>();
		groupDescriptors.add(new CollectGroupDescriptor(QC1, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(QC2, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(QC3, QC, tubes));
		groupDescriptors.add(new CollectGroupDescriptor(DEFAULT, DEFAULT, tubes));

		collectGroupConfiguration = new CollectGroupConfiguration(groupDescriptors);

		expectedSet = new HashSet<>();
		expectedSet.add(new CollectGroupDescriptor(QC1, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(QC2, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(QC3, QC, tubes));
		expectedSet.add(new CollectGroupDescriptor(DEFAULT, DEFAULT, tubes));
	}
	@Test
	public void method_should_getDefaultCollectGroupDescritor() {
		String expectedGetType = expectedSet.stream().filter(group -> group.getType().equals(DEFAULT)).findFirst()
				.get().getType();
		assertEquals(expectedGetType, collectGroupConfiguration.getDefaultCollectGroupDescriptor().getType());
	}
	@Test
	public void method_should_getCollectGroupByName() {
		String expectedGetName = expectedSet.stream().filter(group -> group.getName().equals(QC1)).findFirst().get()
				.getName();
		assertEquals(expectedGetName, collectGroupConfiguration.getCollectGroupByName(QC1).getName());
	}
	@Test
	public void method_should_getCollectGroupsByType() {
		assertEquals(
				expectedSet.stream().filter(group -> group.getType().equals(QC))
						.collect(Collectors.toList()).size(),
				collectGroupConfiguration.getCollectGroupsByType(QC).size());
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
