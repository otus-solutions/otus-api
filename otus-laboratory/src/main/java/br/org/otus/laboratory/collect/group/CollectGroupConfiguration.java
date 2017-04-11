package br.org.otus.laboratory.collect.group;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CollectGroupConfiguration {

	private Set<CollectGroupDescriptor> groupDescriptors;

	public CollectGroupConfiguration(Set<CollectGroupDescriptor> groupDescriptors) {
		this.groupDescriptors = groupDescriptors;
	}

	public CollectGroupDescriptor getDefaultCollectGroupDescriptor() {
		return this.groupDescriptors.stream().filter(group -> group.getType().equals("DEFAULT")).findFirst().get();
	}

	public CollectGroupDescriptor getCollectGroupByName(String groupName) {
		return this.groupDescriptors.stream().filter(group -> group.getName().equals(groupName)).findFirst().get();
	}

	public List<CollectGroupDescriptor> getCollectGroupsByType(String groupType) {
		return this.groupDescriptors.stream().filter(group -> group.getType().equals(groupType)).collect(Collectors.toList());
	}

	public List<CollectGroupDescriptor> listAllGroupDescriptors() {
		return this.groupDescriptors.stream().collect(Collectors.toList());
	}

	public List<String> listGroupNames() {
		return this.groupDescriptors.stream().map(group -> group.getName()).collect(Collectors.toList());
	}

}
