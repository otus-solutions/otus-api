package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.List;

public class AliquotGroupDescriptors {

	private String objectType;
	private String name;
	private List<AliquotMomentDescriptors> aliquotMomentDescriptors;

	public AliquotGroupDescriptors(String objectType, String name, List<AliquotMomentDescriptors> aliquotMomentDescriptors) {
		this.objectType = objectType;
		this.name = name;
		this.aliquotMomentDescriptors = aliquotMomentDescriptors;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public List<AliquotMomentDescriptors> getAliquotMomentDescriptors() {
		return aliquotMomentDescriptors;
	}

}
