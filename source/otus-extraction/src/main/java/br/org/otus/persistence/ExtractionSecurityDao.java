package br.org.otus.persistence;

import br.org.otus.model.User;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;


public interface ExtractionSecurityDao {

  User validateSecurityCredentials(String securityToken) throws DataNotFoundException;

  String getExtractionToken(String email) throws DataNotFoundException;

}