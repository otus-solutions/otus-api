package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonPUTRequestUtility {
  private HttpURLConnection httpConn;
  private DataOutputStream request;

  public JsonPUTRequestUtility(URL requestURL) throws IOException {
    httpConn = (HttpURLConnection) requestURL.openConnection();
    httpConn.setUseCaches(false);
    httpConn.setDoOutput(true);
    httpConn.setDoInput(true);

    httpConn.setRequestMethod("PUT");
    httpConn.setRequestProperty("Connection", "Keep-Alive");
    httpConn.setRequestProperty("Cache-Control", "no-cache");
    httpConn.setRequestProperty("Content-Type", "application/json");
    request = new DataOutputStream(httpConn.getOutputStream());
  }

  public void writeBody(String body) throws IOException {
    this.request.write(body.getBytes(StandardCharsets.UTF_8));
  }

  public String finish() throws IOException {
    String response;

    request.flush();
    request.close();
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