package br.org.otus.importation.participant.api;

import java.util.Set;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.importation.model.ParticipantImport;
import org.ccem.otus.importation.service.ParticipantImportService;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class ParticipantImportationFacade {

	@Inject
	private ParticipantImportService participantImportService;

	public void importParticipantSet(Set<ParticipantImport> participantSet) {
		try {
			participantImportService.importation(participantSet);
		} catch (ValidationException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
