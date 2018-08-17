package br.org.otus.security;

public interface PasswordResetControlDao {

  void addSession(String token);

  void removeSession(String token);

  void findSession(String token);
}
