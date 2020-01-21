package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonGETUtility {
  private HttpURLConnection httpConn;

  public JsonGETUtility(URL requestURL) throws IOException {
    httpConn = (HttpURLConnection) requestURL.openConnection();
    httpConn.setRequestMethod("GET");
    httpConn.setRequestProperty("Connection", "Keep-Alive");
    httpConn.setRequestProperty("Cache-Control", "no-cache");
  }

  public String finish() throws IOException, RequestException {
    String response;

    int status = httpConn.getResponseCode();
    if (status == HttpURLConnection.HTTP_OK) {
      response = RequestUtility.getString(httpConn);
    } else {
      String errorMessage = httpConn.getResponseMessage();
      Object errorContent = RequestUtility.getErrorContent(httpConn);
      throw new RequestException(status, errorMessage, errorContent);
    }

    return response;
  }

}
