package br.org.otus.laboratory.collect.aliquot;

public class AliquoteDescriptor {

	private String objectType;
	private String name;
	private String label;
	private String role;
	private Integer quantity;

	public AliquoteDescriptor(String objectType, String name, String label, String role, Integer quantity) {
		this.objectType = objectType;
		this.name = name;
		this.label = label;
		this.role = role;
		this.quantity = quantity;
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

	public Integer getQuantity() {
		return quantity;
	}
}
