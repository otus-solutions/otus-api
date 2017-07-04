package br.org.otus.laboratory.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.org.otus.laboratory.collect.aliquot.Aliquot;

public class UpdateTubeAliquotsDTO {

	private String tubeCode;
	private List<Aliquot> newAliquots;

	public String getTubeCode() {
		return tubeCode;
	}

	public List<Aliquot> getNewAliquots() {
		return newAliquots;
	}

	public List<Aliquot> getDuplicatesAliquots() {
		List<Aliquot> duplicates = new ArrayList<>();
		Set<String> set = new HashSet<>();
		
		for (Aliquot aliquot : newAliquots) {
			if (!set.add(aliquot.getCode())) {
				duplicates.add(aliquot);
			}
		}

		return duplicates;
	}

}
