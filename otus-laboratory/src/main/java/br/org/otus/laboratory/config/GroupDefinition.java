package br.org.otus.laboratory.config;

import java.util.List;

public class GroupDefinition {

	public List<Group> defaultGroup;
	public List<Group> qualityControlGroup;

	public GroupDefinition(List<Group> defaultGroup,
			List<Group> qualityControlGroup) {
		this.defaultGroup = defaultGroup;
		this.qualityControlGroup = qualityControlGroup;
	}

	public List<Group> getDefaultGroup() {
		return defaultGroup;
	}

	public List<Group> getQualityControlGroup() {
		return qualityControlGroup;
	}

	public void setQualityControlGroup(List<Group> qualityControlGroup) {
		this.qualityControlGroup = qualityControlGroup;
	}
}
