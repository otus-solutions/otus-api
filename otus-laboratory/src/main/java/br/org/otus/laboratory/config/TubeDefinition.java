package br.org.otus.laboratory.config;

public class TubeDefinition {
	private String name;
	private String label;
	private String color;

	public TubeDefinition(String name, String label, String color) {
		this.name = name;
		this.label = label;
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public String getColor() {
		return color;
	}

}
