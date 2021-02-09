package br.org.otus.extraction;

import br.org.otus.gateway.gates.ExtractionGatewayService;
import br.org.otus.gateway.response.exception.RequestException;
import br.org.otus.response.exception.HttpResponseException;
import br.org.otus.response.info.NotFound;
import br.org.otus.response.info.Validation;
import org.ccem.otus.service.extraction.model.Rscript;

import java.io.IOException;
import java.util.logging.Logger;

public class RscriptFacade {

  private final static Logger LOGGER = Logger.getLogger("br.org.otus.extraction.RscriptFacade");

  public void createOrUpdate(String rscriptJson){
    try {
      new ExtractionGatewayService().createOrUpdateRscript(rscriptJson);
      LOGGER.info("status: success, action: create R script " + rscriptJson);
    } catch (IOException e) {
      LOGGER.severe("status: fail, action: create R script " + rscriptJson);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }

  public Rscript get(String rscriptName){
    try {
      String response = (String) new ExtractionGatewayService().getRscript(rscriptName).getData();
      return Rscript.deserialize(response);
    } catch (IOException e) {
      LOGGER.severe("status: fail, action: get R script " + rscriptName);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
    catch(RequestException e){
      LOGGER.severe("status: fail, action: get R script " + rscriptName);
      throw new HttpResponseException(NotFound.build(e.getMessage()));
    }
  }

  public void delete(String rscriptName){
    try {
      new ExtractionGatewayService().deleteRscript(rscriptName);
      LOGGER.info("status: success, action: delete R script " + rscriptName);
    } catch (IOException e) {
      LOGGER.severe("status: fail, action: delete R script " + rscriptName);
      throw new HttpResponseException(Validation.build(e.getMessage()));
    }
  }
}