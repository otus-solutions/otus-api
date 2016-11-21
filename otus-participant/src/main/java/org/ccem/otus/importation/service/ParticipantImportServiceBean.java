package org.ccem.otus.importation.service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.service.ParticipantService;

@Stateless
public class ParticipantImportServiceBean implements ParticipantImportService {

	@Inject
	private ParticipantService participantService;
	
	@Inject
	private ParticipantImportValidatorService participantImportValidatorService;

	@Override
	public void importation(Set<ParticipantImport> participantImports) {
		if(participantImportValidatorService.isImportable(participantImports)) {
			
		}
	}
	
	private void performImportation(Set<ParticipantImport> participantImports) {
		// TODO :
		//	- ParticipantBuilder.build(ParticipantImport participantImport);
	}

	
}
