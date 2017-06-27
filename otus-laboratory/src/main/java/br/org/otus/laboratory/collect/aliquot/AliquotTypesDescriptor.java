package br.org.otus.laboratory.collect.aliquot;

import java.util.List;

public class AliquotTypesDescriptor {

	private String objectType;
	private String name;
	private List<AliquoteGroupDescriptor> aliquotsSet;

	public AliquotTypesDescriptor(String objectType, String name, List<AliquoteGroupDescriptor> aliquotsSet) {
		this.objectType = objectType;
		this.name = name;
		this.aliquotsSet = aliquotsSet;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquoteGroupDescriptor> getAliquotsSet() {
		return aliquotsSet;
	}

}
