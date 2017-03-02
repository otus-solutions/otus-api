package br.org.otus.laboratory.participant;

public class Aliquote {

	private Integer code;
	private String type;
	private Category category;

	public Aliquote(Integer code, String type, Category category) {
		this.code = code;
		this.type = type;
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
