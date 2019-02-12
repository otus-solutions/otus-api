package br.org.otus.laboratory.participant.validators;

import br.org.otus.laboratory.extraction.model.ParticipantLaboratoryRecordExtraction;

import java.util.LinkedList;

public interface ParticipantLaboratoryExtractionDao {

    LinkedList<ParticipantLaboratoryRecordExtraction> getLaboratoryExtractionByParticipant();

}
