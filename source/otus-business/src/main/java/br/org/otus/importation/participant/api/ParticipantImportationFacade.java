package br.org.otus.importation.participant.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.importation.model.ParticipantImport;
import org.ccem.otus.participant.importation.service.ParticipantImportService;

import javax.inject.Inject;
import java.util.Set;

public class ParticipantImportationFacade {

	@Inject
	private ParticipantImportService participantImportService;

	public void importParticipantSet(Set<ParticipantImport> participantSet) {
		try {
			participantImportService.importation(participantSet);
		} catch (ValidationException | DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

}
