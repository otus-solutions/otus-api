package br.org.otus.laboratory.config;

import java.util.List;

public class Group {

	private String name;
	private List<String> groupTubes;

	public Group(String name, List<String> groupTubes) {
		this.name = name;
		this.groupTubes = groupTubes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getTubeList() {
		return groupTubes;
	}

	public void setGroupTubes(List<String> groupTubes) {
		this.groupTubes = groupTubes;
	}

}
