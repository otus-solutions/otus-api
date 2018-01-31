package br.org.otus.laboratory.configuration.collect.aliquot;

import java.util.Objects;

public class AliquoteDescriptor {

	private String objectType;
	private String name;
	private String label;
	private String role;

	public AliquoteDescriptor(String objectType, String name, String label, String role, Integer quantity) {
		this.objectType = objectType;
		this.name = name;
		this.label = label;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AliquoteDescriptor that = (AliquoteDescriptor) o;
		return Objects.equals(objectType, that.objectType) &&
				Objects.equals(name, that.name) &&
				Objects.equals(label, that.label);
	}

	@Override
	public int hashCode() {

		return Objects.hash(objectType, name, label);
	}
}
