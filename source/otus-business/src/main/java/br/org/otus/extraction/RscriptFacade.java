package br.org.otus.extraction;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.exception.NotFoundRequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import org.ccem.otus.exceptions.webservice.validation.ValidationException;
import org.ccem.otus.service.extraction.model.Rscript;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class RscriptFacade {

    private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.RscriptFacade");


    public void createOrUpdate(String rscriptJson) {
        try {
            validateRscriptName(RscriptExtractionData.deserialize(rscriptJson).getName());
            new ExtractionGatewayService().createOrUpdateRscript(rscriptJson);
            LOGGER.info("status: success, action: create R script " + rscriptJson);
        } catch (Exception e) {
            LOGGER.severe("status: fail, action: create R script " + rscriptJson);
            throw new HttpResponseException(Validation.build(e.getMessage()));
        }
    }

    public Rscript get(String rscriptName) {
        try {
            String response = (String) new ExtractionGatewayService().getRscript(rscriptName).getData();
            return Rscript.deserialize(response);
        } catch (NotFoundRequestException e) {
            throw new HttpResponseException(NotFound.build("R script doesn't exists"));
        } catch (Exception e) {
            LOGGER.severe("status: fail, action: get R script " + rscriptName);
            throw new HttpResponseException(Validation.build(e.getMessage()));
        }
    }

    public void delete(String rscriptName) {
        try {
            new ExtractionGatewayService().deleteRscript(rscriptName);
            LOGGER.info("status: success, action: delete R script " + rscriptName);
        } catch (NotFoundRequestException e) {
            throw new HttpResponseException(NotFound.build("R script doesn't exists"));
        } catch (Exception e) {
            LOGGER.severe("status: fail, action: get R script " + rscriptName);
            throw new HttpResponseException(Validation.build(e.getMessage()));
        }
    }

    private void validateRscriptName(String rscriptName) throws ValidationException {
        String REGEX = "[\\w[!$&'()*+,\\-.:=@_~]\\p{Blank}]";
        String bannedCharacters = rscriptName.replaceAll(REGEX, "");
        if (bannedCharacters.length() > 0) {
            throw new ValidationException("Cannot Update or Create R script with the following characters: " + Arrays.toString(bannedCharacters.split("")));
        }
    }
}
