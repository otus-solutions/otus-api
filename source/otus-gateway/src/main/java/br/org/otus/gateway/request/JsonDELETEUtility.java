package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class JsonDELETEUtility {
  private HttpURLConnection httpConn;

  public JsonDELETEUtility(URL requestURL) throws IOException {
    httpConn = (HttpURLConnection) requestURL.openConnection();
    httpConn.setRequestMethod("DELETE");
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
