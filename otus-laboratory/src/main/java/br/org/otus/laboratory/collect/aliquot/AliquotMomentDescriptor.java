package br.org.otus.laboratory.collect.aliquot;

import java.util.List;

public class AliquotMomentDescriptor {

	private String objectType;
	private String name;
	private List<AliquotTypesDescriptor> aliquotTypesDescriptors;

	public AliquotMomentDescriptor(String objectType, String name, List<AliquotTypesDescriptor> aliquotTypesDescriptors) {
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

	public List<AliquotTypesDescriptor> getAliquotTypesDescriptors() {
		return aliquotTypesDescriptors;
	}

}
