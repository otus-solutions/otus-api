package br.org.otus.laboratory.collect.moment;

import java.util.Set;

public class CollectMomentConfiguration {

	private Set<CollectMomentDescriptor> collectMomentDescriptors;

	public CollectMomentConfiguration(Set<CollectMomentDescriptor> collectMomentDescriptors) {
		this.collectMomentDescriptors = collectMomentDescriptors;
	}

	public Set<CollectMomentDescriptor> getMomentDescriptors() {
		return collectMomentDescriptors;
	}

}
