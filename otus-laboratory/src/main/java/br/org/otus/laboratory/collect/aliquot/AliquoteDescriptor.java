package br.org.otus.laboratory.collect.aliquot;

import org.ccem.otus.survey.template.item.label.Label;

public class AliquoteDescriptor {

	private String objectType;
	private String name;
	private Label label;
	private Integer quantity;

	public String getObjectType() {
		return objectType;
	}

	public String getName() {
		return name;
	}

	public Label getLabel() {
		return label;
	}

	public Integer getQuantity() {
		return quantity;
	}
}
