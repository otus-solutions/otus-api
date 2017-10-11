package br.org.otus.extraction;

import br.org.otus.model.User;
import br.org.otus.response.builders.ResponseBuild;
import br.org.otus.response.exception.HttpResponseException;
import org.ccem.otus.exceptions.webservice.common.DataNotFoundException;

import javax.inject.Inject;

public class ExtractionSecurityService {

    @Inject
    private ExtractionSecurityDao ExtractionSecurityDaoBean;

    public Boolean validateSecurityCredentials(String token, String ip){
        try {
            User user = ExtractionSecurityDaoBean.validateSecurityCredentials(token);
            if (!user.getExtractionIps().isEmpty() && user.getExtractionIps().contains(ip)){
                return false;
            } else {
                return true;
            }
        } catch (DataNotFoundException e) {
            throw new HttpResponseException(ResponseBuild.Security.Validation.build(e.getMessage()));
        }
    }
}