package org.ccem.otus.service.extraction.enums;

public enum ExtractionVariables {
	//TODO: Rever quais desses variaveis precisam estar aqui!
	POINT_P(".P"),
	POINT_F(".F");

	private final String value;

	public String getValue() {
		return value;
	}

	private ExtractionVariables(String s) {
		value = s;
	}

	public boolean equalsName(String otherValue) {
		return value.equals(otherValue);
	}

	public String toString() {
		return this.value;
	}
}
