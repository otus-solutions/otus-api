package org.ccem.otus.exceptions;

import org.ccem.otus.exceptions.webservice.security.EncryptedException;

public interface Encripting {
  void encrypt() throws EncryptedException;
}
