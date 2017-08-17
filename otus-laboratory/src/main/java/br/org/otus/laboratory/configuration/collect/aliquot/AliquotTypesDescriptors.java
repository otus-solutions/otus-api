package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.List;

public class AliquotTypesDescriptors {

	private String objectType;
	private String name;
	private List<AliquoteDescriptor> aliquots;

	public AliquotTypesDescriptors(String objectType, String name, List<AliquoteDescriptor> aliquots) {
		this.objectType = objectType;
		this.name = name;
		this.aliquots = aliquots;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquoteDescriptor> getAliquots() {
		return aliquots;
	}

}
