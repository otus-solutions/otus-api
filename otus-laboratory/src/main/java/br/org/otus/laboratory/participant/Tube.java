package br.org.otus.laboratory.participant;

import java.util.List;

public class Tube {
	
	private String type;
	private String code;
	public List<Aliquote> aliquotes;
	
	public Tube(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
