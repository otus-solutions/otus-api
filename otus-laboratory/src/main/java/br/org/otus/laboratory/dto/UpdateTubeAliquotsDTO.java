package br.org.otus.laboratory.dto;

import java.util.List;

import br.org.otus.laboratory.collect.aliquot.Aliquot;

public class UpdateTubeAliquotsDTO {

	private String code;
	private List<Aliquot> aliquotes;

	public String getTubeCode() {
		return code;
	}

	public List<Aliquot> getAliquots() {
		return aliquotes;
	}

}
