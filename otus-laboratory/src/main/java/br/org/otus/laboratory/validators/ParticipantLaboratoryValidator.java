package br.org.otus.laboratory.validators;

import org.ccem.otus.exceptions.webservice.validation.ValidationException;

public interface ParticipantLaboratoryValidator {
	
	AliquotUpdateValidateResponse validate() throws ValidationException;

}
	