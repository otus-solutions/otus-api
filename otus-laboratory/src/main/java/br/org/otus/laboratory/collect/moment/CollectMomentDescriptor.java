package br.org.otus.laboratory.collect.moment;

public class CollectMomentDescriptor {

	private String name;
	private String label;

	public CollectMomentDescriptor(String name, String label) {
		this.name = name;
		this.label = label;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

}
