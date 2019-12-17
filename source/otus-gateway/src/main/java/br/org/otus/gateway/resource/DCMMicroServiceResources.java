package br.org.otus.gateway.resource;

import java.net.MalformedURLException;
import java.net.URL;

import br.org.otus.gateway.MicroservicesEnvironments;

public class DCMMicroServiceResources extends MicroservicesResources {

  private static final String RETINOGRAPHY_IMAGE = "/api/retinography";
  private static final String ULTRASOUND_IMAGE = "/api/ultrasound";

  public DCMMicroServiceResources() {
    super(MicroservicesEnvironments.DCM);
  }

  public URL getRetinographyImageAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + RETINOGRAPHY_IMAGE);
  }

  public URL getUltrasoundImageAddress() throws MalformedURLException {
    return new URL("http://" + this.HOST + ":" + this.PORT + ULTRASOUND_IMAGE);
  }

}
