package br.org.otus.laboratory.configuration.collect.moment;

import java.util.Set;

public class CollectMomentConfiguration {

	private Set<MomentDescriptor> momentDescriptors;

	public CollectMomentConfiguration(Set<MomentDescriptor> momentDescriptors) {
		this.momentDescriptors = momentDescriptors;
	}

	public Set<MomentDescriptor> getMomentDescriptors() {
		return momentDescriptors;
	}

}
