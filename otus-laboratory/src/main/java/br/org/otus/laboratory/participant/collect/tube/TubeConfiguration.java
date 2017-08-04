package br.org.otus.laboratory.participant.collect.tube;

import java.util.Set;

public class TubeConfiguration {

	private Set<TubeDescriptor> tubeDescriptors;

	public TubeConfiguration(Set<TubeDescriptor> tubeDescriptors) {
		this.tubeDescriptors = tubeDescriptors;
	}

	public Set<TubeDescriptor> getTubeDescriptors() {
		return tubeDescriptors;
	}

}
