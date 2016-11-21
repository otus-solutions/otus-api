package org.ccem.otus.importation.service;

import java.util.Set;

import org.ccem.otus.importation.model.ParticipantImport;

public interface ParticipantImportValidatorService {

	boolean isImportable(Set<ParticipantImport> participantImports);
	
}
