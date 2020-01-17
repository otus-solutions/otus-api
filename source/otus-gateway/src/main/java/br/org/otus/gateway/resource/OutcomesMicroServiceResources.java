package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class OutcomesMicroServiceResources extends MicroservicesResources {
  private static final String CREATE_FOLLOWUP_RESOURCE = "/create";
  private static final String UPDATE_FOLLOWUP_RESOURCE = "/update";
  private static final String DEACTIVATED_FOLLOWUP_RESOURCE = "/deactivate";
  private static final String LIST_FOLLOWUPS_RESOURCE = "/list";

  public OutcomesMicroServiceResources() {
    super(MicroservicesEnvironments.OUTCOMES);
  }

  public URL getCreateFollowUpAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + CREATE_FOLLOWUP_RESOURCE);
  }

  public URL getUpdateFollowUpAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + UPDATE_FOLLOWUP_RESOURCE);
  }

  public URL getDeactivateFollowUpsAddress(String followUpId) throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + DEACTIVATED_FOLLOWUP_RESOURCE + "/" + followUpId);
  }

  public URL getListFollowUpsAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + LIST_FOLLOWUPS_RESOURCE);
  }

}
