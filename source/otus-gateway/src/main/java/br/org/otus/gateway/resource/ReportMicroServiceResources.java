package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class ReportMicroServiceResources extends MicroservicesResources {

  private static final String REPORT_TEMPLATE = "/report";

  public ReportMicroServiceResources() {
    super(MicroservicesEnvironments.REPORT);
  }

  public URL getReportTemplate() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + REPORT_TEMPLATE);
  }
}
