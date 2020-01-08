package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class OutcomesMicroServiceResources extends MicroservicesResources {
  private static final String CREATE_OUTCOME_RESOURCE = "/create";
  private static final String UPDATE_OUTCOME_RESOURCE = "/update";
  private static final String LIST_OUTCOMES_RESOURCE = "/list";

  public OutcomesMicroServiceResources() {
    super(MicroservicesEnvironments.OUTCOMES);
  }

  public URL getCreateOutcomeAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CREATE_OUTCOME_RESOURCE);
  }

  public URL getUpdateOutcomeAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_OUTCOME_RESOURCE);
  }

  public URL getListOutcomesAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + LIST_OUTCOMES_RESOURCE);
  }

}
