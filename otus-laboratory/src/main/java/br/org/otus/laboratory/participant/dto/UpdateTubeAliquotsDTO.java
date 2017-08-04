package br.org.otus.laboratory.participant.dto;

import java.util.List;

import br.org.otus.laboratory.participant.collect.aliquot.Aliquot;

public class UpdateTubeAliquotsDTO {

	private String code;
	private List<Aliquot> aliquots;

	public String getTubeCode() {
		return code;
	}

	public List<Aliquot> getAliquots() {
		return aliquots;
	}

}
