package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public class JsonDELETEUtility extends JsonRequestUtility {

  public JsonDELETEUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.DELETE, requestURL);
  }

}
