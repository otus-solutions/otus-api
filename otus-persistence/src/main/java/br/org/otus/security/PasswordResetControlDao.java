package br.org.otus.security;

public interface PasswordResetControlDao {

  void registerToken(String token, String email);

  void removeRegister(String token);

  void removeAllRegisters(String email);

  boolean tokenExists(String token);

  boolean hasRegister(String email);
}
