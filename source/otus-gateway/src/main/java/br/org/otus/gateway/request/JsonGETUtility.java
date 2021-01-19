package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonGETUtility extends JsonRequestUtility {

  protected DataOutputStream request;

  public JsonGETUtility(URL requestURL) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
  }

  public JsonGETUtility(URL requestURL, String body) throws IOException {
    super(RequestTypeOptions.GET, requestURL);
    httpConn.setDoOutput(true);
    httpConn.setRequestProperty("Content-Type", "application/json");
    request = new DataOutputStream(httpConn.getOutputStream());
    this.request.write(body.getBytes(StandardCharsets.UTF_8));
  }

  @Override
  public String finish() throws IOException, RequestException {
    if(request != null){
      request.flush();
      request.close();
    }
    return super.finish();
  }

}
