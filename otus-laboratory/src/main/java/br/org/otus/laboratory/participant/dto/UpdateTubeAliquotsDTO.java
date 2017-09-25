package br.org.otus.laboratory.participant.dto;

import br.org.otus.laboratory.participant.aliquot.Aliquot;

import java.util.List;

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
