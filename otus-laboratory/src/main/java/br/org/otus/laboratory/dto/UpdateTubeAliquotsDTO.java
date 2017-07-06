package br.org.otus.laboratory.dto;

import java.util.List;

import br.org.otus.laboratory.collect.aliquot.Aliquot;

public class UpdateTubeAliquotsDTO {

	private String tubeCode;
	private List<Aliquot> aliquots;

	public String getTubeCode() {
		return tubeCode;
	}

	public List<Aliquot> getAliquots() {
		return aliquots;
	}

}
