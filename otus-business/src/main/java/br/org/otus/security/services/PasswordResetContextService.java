package br.org.otus.security.services;

import br.org.otus.security.dtos.PasswordResetRequestDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface PasswordResetContextService {

  void registerToken(PasswordResetRequestDto requestData);

  void removeRequests(String email);

  void removeToken(String token);

  Boolean hasToken(String token);

  String getRequestEmail(String token) throws DataNotFoundException;
}
