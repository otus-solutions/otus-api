package br.org.otus.laboratory.validators;

import java.util.ArrayList;
import java.util.List;

import br.org.otus.laboratory.collect.aliquot.Aliquot;

public class AliquotUpdateValidateResponse {

	private static final String NAME = "AliquotUpdateValidateResponse";
	private List<Aliquot> conflicts;
	
	public AliquotUpdateValidateResponse() {
		this.conflicts = new ArrayList<Aliquot>();
	}

	public List<Aliquot> getConflicts() {
		return conflicts;
	}

	public void setConflicts(List<Aliquot> conflicts) {
		this.conflicts = conflicts;
	}
	
	public boolean isValid() {
		return conflicts.isEmpty();
	}
	
}
