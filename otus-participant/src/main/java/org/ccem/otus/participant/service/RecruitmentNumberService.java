package org.ccem.otus.participant.service;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;

public interface RecruitmentNumberService {

  Long get(String centerAcronym) throws DataNotFoundException, ValidationException;

}
