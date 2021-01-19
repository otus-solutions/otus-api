package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;

public class JsonPOSTUtility extends JsonWritingRequestUtility {

  public JsonPOSTUtility(URL requestURL, String body) throws IOException {
    super(RequestTypeOptions.POST, requestURL, body);
  }

}
