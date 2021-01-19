package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public abstract class JsonWritingRequestUtility extends JsonRequestUtility {

  public JsonWritingRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL) throws IOException {
    super(requestTypeOption, requestURL);
    setMoreHttpConnProperties();
  }

  public JsonWritingRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL, String body) throws IOException {
    super(requestTypeOption, requestURL, body);
    setMoreHttpConnProperties();
  }

  private void setMoreHttpConnProperties(){
    httpConn.setUseCaches(false);
    httpConn.setDoOutput(true);
    httpConn.setDoInput(true);
    httpConn.setRequestProperty("Content-Type", "application/json");
  }
}
