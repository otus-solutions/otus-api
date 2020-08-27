package br.org.otus.commons;

import br.org.otus.model.User;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.participant.model.Participant;

import java.text.ParseException;

public interface FindByTokenService {

  User findUserByToken(String token) throws DataNotFoundException, ValidationException, ParseException;

  Participant findParticipantByToken(String token) throws DataNotFoundException, ValidationException, ParseException;

  Object findPersonByToken(String token) throws ParseException, DataNotFoundException, ValidationException;
}
