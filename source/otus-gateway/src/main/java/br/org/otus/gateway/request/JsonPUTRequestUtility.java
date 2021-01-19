package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public class JsonPUTRequestUtility extends JsonWritingRequestUtility{

  public JsonPUTRequestUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.PUT, requestURL);
  }

  public JsonPUTRequestUtility(URL requestURL, String body) throws IOException {
    super(RequestTypeOptions.PUT, requestURL, body);
  }

}
