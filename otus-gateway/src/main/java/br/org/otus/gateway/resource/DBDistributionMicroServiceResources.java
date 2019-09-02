package br.org.otus.gateway.resource;

import java.net.MalformedURLException;
import java.net.URL;

import br.org.otus.gateway.MicroservicesEnvironments;

public class DBDistributionMicroServiceResources extends MicroservicesResources {
  private static final String FIND_VARIABLES_RESOURCE = "/api/findVariables";
  private static final String DATABASE_UPLOAD_RESOURCE = "/api/upload/database";
  private static final String EXAMS_IMAGE = "/api/examsImage";
  private static final String VARIABLE_TYPE_CORRELATION_UPLOAD_RESOURCE = "/api/upload/variable-type-correlation";

  public DBDistributionMicroServiceResources() {
    super(MicroservicesEnvironments.DBDISTRIBUTION);
  }

  public URL getFindVariableAddress() throws MalformedURLException {
    return new URL(this.HOST + this.PORT + FIND_VARIABLES_RESOURCE);
  }

  public URL getDatabaseUploadAddress() throws MalformedURLException {
    return new URL(this.HOST + this.PORT + DATABASE_UPLOAD_RESOURCE);
  }

  public URL getVariableTypeCorrelationUploadAddress() throws MalformedURLException {
    return new URL(this.HOST + this.PORT + VARIABLE_TYPE_CORRELATION_UPLOAD_RESOURCE);
  }

  public URL getExamsImageAddress() throws MalformedURLException {
    return new URL(this.HOST + this.PORT + EXAMS_IMAGE);
  }
}
