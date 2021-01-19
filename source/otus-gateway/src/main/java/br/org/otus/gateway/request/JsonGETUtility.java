package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public class JsonGETUtility extends JsonRequestUtility {

  public JsonGETUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
  }

  public JsonGETUtility(URL requestURL, String body) throws IOException {
    super(RequestTypeOptions.POST, requestURL, body);
  }

}
