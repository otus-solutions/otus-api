package br.org.otus.participant.api;

import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.participant.model.Participant;
import org.ccem.otus.participant.service.ParticipantService;

import javax.inject.Inject;
import java.util.List;

public class ParticipantFacade {

	@Inject
	private ParticipantService participantService;
	
	public Participant getByRecruitmentNumber(long rn) {
		Participant participant = null;
		
		try {
			participant= participantService.getByRecruitmentNumber(rn);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
		
		return participant;
	}

	public List<Participant> listAll() {
		return participantService.list();
	}
	
}
