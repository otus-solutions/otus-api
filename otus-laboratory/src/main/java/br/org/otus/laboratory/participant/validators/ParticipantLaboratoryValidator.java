package br.org.otus.laboratory.participant.validators;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

public interface ParticipantLaboratoryValidator {

	AliquotUpdateValidateResponse validate() throws ValidationException, DataNotFoundException;

}
