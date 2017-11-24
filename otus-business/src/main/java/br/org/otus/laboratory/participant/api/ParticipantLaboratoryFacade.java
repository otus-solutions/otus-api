package br.org.otus.laboratory.participant.api;

import javax.inject.Inject;

import br.org.otus.laboratory.participant.tube.Tube;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.participant.dto.UpdateAliquotsDTO;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;

public class ParticipantLaboratoryFacade {

	@Inject
	private ParticipantLaboratoryService service;

	public ParticipantLaboratory update(ParticipantLaboratory participantLaboratory) {
		try {
			return service.update(participantLaboratory);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public Tube updateTubeCollectionData(Long rn, Tube tube) {
		try {
			return service.updateTubeCollectionData(rn,tube);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public ParticipantLaboratory getLaboratory(Long recruitmentNumber) {
		try {
			return service.getLaboratory(recruitmentNumber);
		} catch (DataNotFoundException e) {
			// e.printStackTrace();
			// throw new
			// HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
			return null;
		}
	}

	public ParticipantLaboratory create(Long recruitmentNumber) {
		try {
			return service.create(recruitmentNumber);
		} catch (DataNotFoundException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage()));
		}
	}

	public boolean hasLaboratory(Long recruitmentNumber) {
		return service.hasLaboratory(recruitmentNumber);
	}

	public ParticipantLaboratory updateAliquotList(UpdateAliquotsDTO updateAliquots) {
		try {
			return service.updateAliquots(updateAliquots);
		} catch (DataNotFoundException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		} catch (ValidationException e) {
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), e.getData()));
		}
	}

}
