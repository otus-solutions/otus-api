package br.org.otus.laboratory.participant.collect.aliquot;

import java.util.List;

public class AliquotMomentDescriptors {

	private String objectType;
	private String name;
	private List<AliquotTypesDescriptors> aliquotTypesDescriptors;

	public AliquotMomentDescriptors(String objectType, String name, List<AliquotTypesDescriptors> aliquotTypesDescriptors) {
		this.objectType = objectType;
		this.name = name;
		this.aliquotTypesDescriptors = aliquotTypesDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquotTypesDescriptors> getAliquotTypesDescriptors() {
		return aliquotTypesDescriptors;
	}

}
