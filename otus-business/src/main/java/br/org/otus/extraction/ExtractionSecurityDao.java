package br.org.otus.extraction;

import br.org.otus.model.User;
import br.org.otus.user.dto.ManagementUserDto;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;


public interface ExtractionSecurityDao {

    User validateSecurityCredentials(String securityToken) throws DataNotFoundException;

    String getExtractionToken(String email) throws DataNotFoundException;

}