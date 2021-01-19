package br.org.otus.gateway.request;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

public class JsonGETUtility extends JsonRequestUtility {

  protected DataOutputStream request;

  public JsonGETUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
  }

}
