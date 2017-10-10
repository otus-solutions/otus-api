package br.org.otus.extraction;

import br.org.otus.model.User;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;


public interface ExtractionSecurityDao {

    User validateSecurityCredentials(String securityToken, String ip) throws DataNotFoundException;

}