package br.org.otus.laboratory.participant.aliquot;

import java.time.LocalDateTime;

public class AliquotCollectionData {

	private String objectType;
	private String metadata;
	private String operator;
	private LocalDateTime time;
	private LocalDateTime processing;
	

	public AliquotCollectionData(String metadata, String operator, LocalDateTime time, LocalDateTime processing) {
		this.metadata = metadata;
		this.operator = operator;
		this.time = time;
		this.processing = processing;
	}

	public String getMetadata() {
		return metadata;
	}

	public String getOperator() {
		return operator;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public String getObjectType() {
		return objectType;
	}
	
	public LocalDateTime getProcessing() {
		return processing;
	}

}
