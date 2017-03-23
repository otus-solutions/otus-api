package br.org.otus.laboratory.exam;

import java.util.List;

public class Category {

	private String name;
	public List<String> availableExams;

	public Category(String name, List<String> availableExams) {
		this.name = name;
		this.availableExams = availableExams;
	}

	public String getName() {
		return name;
	}

	public List<String> getAvailableExams() {
		return availableExams;
	}

}
