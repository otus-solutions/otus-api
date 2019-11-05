package br.org.otus.laboratory.configuration.collect.aliquot;

public class CenterAliquot {

	private String name;
	private String role;

	public CenterAliquot(String name, String role) {
		this.name = name;
		this.role = role;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
