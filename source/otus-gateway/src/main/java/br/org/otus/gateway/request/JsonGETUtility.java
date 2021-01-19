package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonGETUtility extends JsonRequestUtility {

  public JsonGETUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
  }

  public JsonGETUtility(URL requestURL, String body) throws IOException {
    super(RequestTypeOptions.POST, requestURL, "application/json");
    request.write(body.getBytes(StandardCharsets.UTF_8));
  }

}
