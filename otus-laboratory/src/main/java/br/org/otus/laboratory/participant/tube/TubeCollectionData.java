package br.org.otus.laboratory.participant.tube;

import java.time.LocalDateTime;

public class TubeCollectionData {

	private String objectType;
	private boolean isCollected;
	private String metadata;
	private String operator;
	private LocalDateTime time;

	public TubeCollectionData() {
		this.objectType = "TubeCollectionData";
		this.isCollected = false;
		this.metadata = "";
		this.operator = "";
	}

	public String getObjectType() {
		return objectType;
	}

	public boolean isCollected() {
		return isCollected;
	}

	public String getMetadata() {
		return metadata;
	}

	public String getOperatorEmail() {
		return operator;
	}

	public LocalDateTime getTime() {
		return time;
	}

}
