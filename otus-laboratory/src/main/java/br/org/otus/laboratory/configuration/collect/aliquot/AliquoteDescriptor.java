package br.org.otus.laboratory.configuration.collect.aliquot;

public class AliquoteDescriptor {

	private String objectType;
	private String name;
	private String label;
	private String role;

	public AliquoteDescriptor(String objectType, String name, String label, String role, Integer quantity) {
		this.objectType = objectType;
		this.name = name;
		this.label = label;
		this.role = role;
	}

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getRole() {
		return role;
	}

}
