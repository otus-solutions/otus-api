package br.org.otus.laboratory.api;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

import br.org.otus.laboratory.dto.UpdateAliquotsDTO;
import br.org.otus.laboratory.participant.ParticipantLaboratory;
import br.org.otus.laboratory.participant.ParticipantLaboratoryService;
import br.org.otus.laboratory.validators.AliquotUpdateValidateResponse;
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

	public void updateAliquotList(UpdateAliquotsDTO updateAliquots) {
		AliquotUpdateValidateResponse updateAliquots2 = null;
		try {
			updateAliquots2 = service.updateAliquots(updateAliquots);
			updateAliquots2.isValid();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build("TESTE", updateAliquots2));
		} catch (DataNotFoundException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			e.printStackTrace();
			throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getCause().getMessage(), updateAliquots2));
		}
	}

}
