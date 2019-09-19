package br.org.otus.laboratory.configuration.collect.tube;

public class TubeDefinition {

	private Integer count;
	private String type;
	private String moment;
	private String group;

	public TubeDefinition(Integer count, String type, String moment) {
		this.count = count;
		this.type = type;
		this.moment = moment;
		this.group = "DEFAULT";
	}

	public Integer getCount() {
		return count;
	}

	public String getType() {
		return type;
	}

	public String getMoment() {
		return moment;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String groupName) {
		this.group = groupName;
	}

}
