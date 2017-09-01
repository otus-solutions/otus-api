package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.List;

public class AliquotCenterDescriptors {

	private String objectType;
	private String name;
	private Integer aliquotCodeSize;
	private List<AliquotGroupDescriptors> aliquotGroupDescriptors;

	public AliquotCenterDescriptors(String objectType, String name, List<AliquotGroupDescriptors> aliquotGroupDescriptors, Integer aliquotCodeSize) {
		this.objectType = objectType;
		this.name = name;
		this.aliquotGroupDescriptors = aliquotGroupDescriptors;
		this.aliquotCodeSize = aliquotCodeSize;
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

	public Integer getAliquotCodeSize() {
		return aliquotCodeSize;
	}
}
