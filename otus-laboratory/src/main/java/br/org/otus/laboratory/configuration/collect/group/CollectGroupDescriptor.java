package br.org.otus.laboratory.configuration.collect.group;

import java.util.Set;

import br.org.otus.laboratory.configuration.collect.tube.TubeDefinition;

public class CollectGroupDescriptor {

	private String name;
	private String type;
	private Set<TubeDefinition> tubeSet;

	public CollectGroupDescriptor(String name, String type, Set<TubeDefinition> tubeSet) {
		this.name = name;
		this.type = type;
		this.tubeSet = tubeSet;
		
		
	}

	public String getName() {
		return this.name;
	}

	public String getType() {
		return type;
	}

	public Set<TubeDefinition> getTubes() {
		return tubeSet;
	}

}
