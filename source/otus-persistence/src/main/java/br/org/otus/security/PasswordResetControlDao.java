package br.org.otus.security;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

public interface PasswordResetControlDao {

  void registerToken(String token, String email);

  void removeRegister(String token);

  void removeAllRegisters(String email);

  boolean tokenExists(String token);

  boolean hasRegister(String email);

  String getRequestEmail(String token) throws DataNotFoundException;
}
