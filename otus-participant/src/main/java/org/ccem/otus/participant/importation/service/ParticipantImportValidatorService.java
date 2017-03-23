package org.ccem.otus.participant.importation.service;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.importation.model.ParticipantImport;

import java.util.Set;

public interface ParticipantImportValidatorService {

	boolean isImportable(Set<ParticipantImport> participantImports) throws ValidationException;
	
}
