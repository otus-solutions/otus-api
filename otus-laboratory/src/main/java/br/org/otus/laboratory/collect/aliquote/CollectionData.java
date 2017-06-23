package br.org.otus.laboratory.collect.aliquote;

import java.time.LocalDateTime;

public class CollectionData {

	private String metadata;
	private String operator;
	private LocalDateTime time;

	public CollectionData(String metadata, String operator, LocalDateTime time) {
		this.metadata = metadata;
		this.operator = operator;
		this.time = time;
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

}
