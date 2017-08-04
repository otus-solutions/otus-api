package br.org.otus.laboratory.participant.collect.aliquot;

import java.util.List;

public class AliquotConfiguration {

	private String objectType;
	private List<AliquotCenterDescriptors> aliquotCenterDescriptors;

	public AliquotConfiguration(List<AliquotCenterDescriptors> aliquotCenterDescriptors) {
		this.aliquotCenterDescriptors = aliquotCenterDescriptors;
	}

	public List<AliquotCenterDescriptors> getAliquotCenterDescriptors() {
		return aliquotCenterDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

}
