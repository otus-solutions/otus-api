package br.org.otus.laboratory.participant.collect.aliquot;

import java.util.List;

public class AliquotCenterDescriptors {

	private String objectType;
	private String name;
	private List<AliquotGroupDescriptors> aliquotGroupDescriptors;

	public AliquotCenterDescriptors(String objectType, String name, List<AliquotGroupDescriptors> aliquotGroupDescriptors) {
		this.objectType = objectType;
		this.name = name;
		this.aliquotGroupDescriptors = aliquotGroupDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquotGroupDescriptors> getAliquotGroupDescriptors() {
		return aliquotGroupDescriptors;
	}

}
