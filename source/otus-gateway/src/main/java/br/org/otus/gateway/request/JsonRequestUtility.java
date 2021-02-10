package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.NotFoundRequestException;
import br.org.otus.gateway.response.exception.RequestException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public abstract class JsonRequestUtility {
  protected HttpURLConnection httpConn;

  public JsonRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL) throws IOException {
    httpConn = (HttpURLConnection) requestURL.openConnection();
    httpConn.setRequestMethod(requestTypeOption.getName());
    httpConn.setRequestProperty("Connection", "Keep-Alive");
    httpConn.setRequestProperty("Cache-Control", "no-cache");
  }

  public String finish() throws IOException, RequestException {
    int status = httpConn.getResponseCode();
    if(status == HttpURLConnection.HTTP_NOT_FOUND){
      throw new NotFoundRequestException();
    }
    if (status != HttpURLConnection.HTTP_OK) {
      String errorMessage = httpConn.getResponseMessage();
      Object errorContent = RequestUtility.getErrorContent(httpConn);
      throw new RequestException(status, errorMessage, errorContent);
    }

    return RequestUtility.getString(httpConn);
  }
}