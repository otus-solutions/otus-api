package br.org.otus.laboratory.participant.validators;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.participant.collect.aliquot.Aliquot;

public class AliquotUpdateValidateResponse {

	private static final String NAME = "AliquotUpdateValidateResponse";
	private List<Aliquot> conflicts;
	private List<String> tubesNotFound;

	public AliquotUpdateValidateResponse() {
		this.conflicts = new ArrayList<Aliquot>();
		this.tubesNotFound = new ArrayList<String>();
	}

	public List<Aliquot> getConflicts() {
		return conflicts;
	}

	public void setConflicts(List<Aliquot> conflicts) {
		this.conflicts = conflicts;
	}

	public List<String> getTubesNotFound() {
		return tubesNotFound;
	}

	public void setTubesNotFound(List<String> tubesNotFound) {
		this.tubesNotFound = tubesNotFound;
	}
	
	public boolean isValid() {
		return conflicts.isEmpty() && tubesNotFound.isEmpty();
	}
}
