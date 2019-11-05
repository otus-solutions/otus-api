package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.extraction.model.LaboratoryRecordExtraction;

import java.util.LinkedList;

public interface ParticipantLaboratoryExtractionDao {

    LinkedList<LaboratoryRecordExtraction> getLaboratoryExtraction();

}
