package br.org.otus.laboratory.collect.aliquot;

import java.util.List;

public class AliquotTypesDescriptor {

	private String objectType;
	private String name;
	private List<AliquoteGroupDescriptor> aliquoteGroupDescriptors;

	public AliquotTypesDescriptor(String objectType, String name, List<AliquoteGroupDescriptor> aliquoteGroupDescriptors) {
		this.objectType = objectType;
		this.name = name;
		this.aliquoteGroupDescriptors = aliquoteGroupDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquoteGroupDescriptor> getAliquoteGroupDescriptors() {
		return aliquoteGroupDescriptors;
	}

}
