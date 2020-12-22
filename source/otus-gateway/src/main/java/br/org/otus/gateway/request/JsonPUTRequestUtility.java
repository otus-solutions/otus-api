package br.org.otus.gateway.request;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonPUTRequestUtility extends JsonWritingRequestUtility{

  public JsonPUTRequestUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.PUT, requestURL, "application/json");
  }

  public void writeBody(String body) throws IOException {
    this.request.write(body.getBytes(StandardCharsets.UTF_8));
  }

}
