package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URL;

//public abstract class JsonWritingRequestUtility extends JsonRequestUtility {
//
//  public JsonWritingRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL) throws IOException {
//    super(requestTypeOption, requestURL);
//    setMoreHttpConnProperties();
//  }
//
//  public JsonWritingRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL, String body) throws IOException {
//    super(requestTypeOption, requestURL);
//    setMoreHttpConnProperties();
//    setRequest(body);
//  }
//
//  private void setMoreHttpConnProperties(){
//    httpConn.setUseCaches(false);
//    httpConn.setDoOutput(true);
//    httpConn.setDoInput(true);
//    httpConn.setRequestProperty("Content-Type", "application/json");
//  }
//}

public abstract class JsonWritingRequestUtility extends JsonRequestUtility {

  protected DataOutputStream request;

  public JsonWritingRequestUtility(RequestTypeOptions requestTypeOption, URL requestURL, String contentType) throws IOException {
    super(requestTypeOption, requestURL);
    httpConn.setUseCaches(false);
    httpConn.setDoOutput(true);
    httpConn.setDoInput(true);
    httpConn.setRequestProperty("Content-Type", contentType);
    request = new DataOutputStream(httpConn.getOutputStream());
  }

  @Override
  public String finish() throws IOException, RequestException {
    request.flush();
    request.close();
    return super.finish();
  }
}