package br.org.otus.laboratory.configuration.label;

public class LabelReference {

	private String groupName;
	private String type;
	private String moment;

	public LabelReference(String groupName, String type, String moment) {
		this.groupName = groupName;
		this.type = type;
		this.moment = moment;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getType() {
		return this.type;
	}

	public String getMoment() {
		return this.moment;
	}

}
