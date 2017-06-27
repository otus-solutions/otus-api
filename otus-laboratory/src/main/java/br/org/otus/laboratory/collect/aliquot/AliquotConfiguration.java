package br.org.otus.laboratory.collect.aliquot;

import java.util.List;

public class AliquotConfiguration {

	private String objectType;
	private List<AliquotMomentDescriptor> aliquotMomentDescriptors;

	public AliquotConfiguration(List<AliquotMomentDescriptor> aliquotMomentDescriptors) {
		this.aliquotMomentDescriptors = aliquotMomentDescriptors;
	}

	public List<AliquotMomentDescriptor> getAliquotMomentDescriptors() {
		return aliquotMomentDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

}
