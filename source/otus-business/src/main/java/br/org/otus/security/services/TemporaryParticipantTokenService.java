package br.org.otus.security.services;

import br.org.otus.security.dtos.ParticipantTempTokenRequestDto;
import org.ccem.otus.exceptions.webservice.security.TokenException;

public interface TemporaryParticipantTokenService {

  String generateTempToken(ParticipantTempTokenRequestDto requestData) throws TokenException;

}
