package br.org.otus.laboratory.project.transportation.validarors;

import br.org.otus.laboratory.project.transportation.aliquot.TransportationAliquot;

import java.util.ArrayList;

public class TransportationLotValidationResult {

	private boolean isValid;

	public boolean isValid() {
		return isValid;
	}

	public ArrayList<TransportationAliquot> getValue() {
		return value;
	}

	public void setValid(boolean valid) {

		isValid = valid;
	}

	public void setValue(ArrayList<TransportationAliquot> value) {
		this.value = value;
	}

	public void pushConflict(TransportationAliquot aliquot) {
		this.value.add(aliquot);
	}

	private ArrayList<TransportationAliquot> value;

	public TransportationLotValidationResult() {
	}
}
