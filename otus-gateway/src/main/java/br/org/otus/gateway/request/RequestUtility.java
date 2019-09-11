package br.org.otus.gateway.request;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

import org.bson.Document;

import com.google.gson.GsonBuilder;

public class RequestUtility {

  public static String getString(HttpURLConnection httpConn) throws IOException {
    String response;
    InputStream responseStream = new BufferedInputStream(httpConn.getInputStream());

    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream));

    String line = "";
    StringBuilder stringBuilder = new StringBuilder();

    while ((line = responseStreamReader.readLine()) != null) {
      stringBuilder.append(line).append("\n");
    }
    responseStreamReader.close();

    response = stringBuilder.toString();
    httpConn.disconnect();
    return new GsonBuilder().create().toJson(new GsonBuilder().create().fromJson(response, Document.class).get("data"));
  }

}
