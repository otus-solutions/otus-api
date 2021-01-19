package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public class JsonGETUtility extends JsonRequestUtility {

  public JsonGETUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
  }

}
