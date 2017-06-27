package br.org.otus.laboratory.collect.aliquot;

import java.util.List;

public class AliquoteGroupDescriptor {

	private String objectType;
	private String name;
	private List<AliquoteDescriptor> aliquots;

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
