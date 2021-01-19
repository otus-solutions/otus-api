package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public abstract class JsonRequestUtility {

  protected HttpURLConnection httpConn;
  protected DataOutputStream request;

  public JsonRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL) throws IOException {
    setHttpConn(requestTypeOption, requestURL);
  }

  public JsonRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL, String body) throws IOException {
    setHttpConn(requestTypeOption, requestURL);
    httpConn.setDoOutput(true);
    this.request = new DataOutputStream(httpConn.getOutputStream());
    writeBody(body);
  }

  private void setHttpConn(RequestTypeOptions requestTypeOption, URL requestURL) throws IOException {
    httpConn = (HttpURLConnection) requestURL.openConnection();
    httpConn.setRequestMethod(requestTypeOption.getName());
    httpConn.setRequestProperty("Connection", "Keep-Alive");
    httpConn.setRequestProperty("Cache-Control", "no-cache");
  }


  public void writeBody(String body) throws IOException {
    this.request.write(body.getBytes(StandardCharsets.UTF_8));
  }

  public String finish() throws IOException, RequestException {
    if(request != null){
      request.flush();
      request.close();
    }

    int status = httpConn.getResponseCode();
    if (status != HttpURLConnection.HTTP_OK) {
      String errorMessage = httpConn.getResponseMessage();
      Object errorContent = RequestUtility.getErrorContent(httpConn);
      throw new RequestException(status, errorMessage, errorContent);
    }

    return RequestUtility.getString(httpConn);
  }
}
