package org.ccem.otus.importation.service;

import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.ccem.otus.builder.ParticipantBuilder;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.model.Participant;
import org.ccem.otus.persistence.FieldCenterDao;
import org.ccem.otus.service.ParticipantService;

@Stateless
public class ParticipantImportServiceBean implements ParticipantImportService {

	@Inject
	private ParticipantService participantService;

	@Inject
	private FieldCenterDao fieldCenterDao;

	@Inject
	private ParticipantImportValidatorService participantImportValidatorService;

	@Override
	public void importation(Set<ParticipantImport> participantImports) throws ValidationException {
		performImportation(participantImports);
	}

	private void performImportation(Set<ParticipantImport> participantImports) throws ValidationException {
		participantImportValidatorService.isImportable(participantImports);
		ParticipantBuilder participantBuilder = new ParticipantBuilder(fieldCenterDao.find());

		for (ParticipantImport participantImport : participantImports) {
			Participant participant = participantBuilder.buildFromPartipantToImport(participantImport);
			participantService.create(participant);
		}
	}

}
