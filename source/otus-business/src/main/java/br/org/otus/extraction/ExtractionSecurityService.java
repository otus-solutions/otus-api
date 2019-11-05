package br.org.otus.extraction;

import javax.inject.Inject;

import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import br.org.otus.model.User;
import br.org.otus.persistence.ExtractionSecurityDao;

public class ExtractionSecurityService {

  @Inject
  private ExtractionSecurityDao ExtractionSecurityDaoBean;

  public Boolean validateSecurityCredentials(String token, String ip) throws DataNotFoundException {
    User user = ExtractionSecurityDaoBean.validateSecurityCredentials(token);
    if (!user.getExtractionIps().isEmpty() && !user.getExtractionIps().contains(ip)) {
      return false;
    } else {
      return true;
    }
  }

  public String getExtractionToken(String email) throws DataNotFoundException {
    return ExtractionSecurityDaoBean.getExtractionToken(email);
  }
}