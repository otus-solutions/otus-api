package br.org.otus.laboratory.configuration.collect.tube;

public class TubeDescriptor {

	private String name;
	private String label;
	private String color;

	public TubeDescriptor(String name, String label, String color) {
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
