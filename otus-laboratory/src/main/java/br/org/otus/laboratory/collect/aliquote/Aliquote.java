package br.org.otus.laboratory.collect.aliquote;

import br.org.otus.laboratory.exam.Category;

public class Aliquote {

	private Integer code;
	private String type;
	private Category category;

	public Aliquote(Integer code, String type, Category category) {
		this.code = code;
		this.type = type;
		this.category = category;
	}

	public Integer getCode() {
		return code;
	}

	public String getType() {
		return type;
	}

	public Category getCategory() {
		return category;
	}

}
