package org.ccem.otus.participant.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.model.FieldCenter;

public interface RecruitmentNumberService {

  Long get(String centerAcronym) throws DataNotFoundException, ValidationException;

  void validate(FieldCenter fieldCenter, Long recruitmentNumber) throws ValidationException;
}
