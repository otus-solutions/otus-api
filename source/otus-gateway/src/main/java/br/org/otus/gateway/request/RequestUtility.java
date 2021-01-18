package br.org.otus.gateway.request;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import org.bson.Document;

import com.google.gson.GsonBuilder;

import static java.nio.charset.StandardCharsets.UTF_8;

public class RequestUtility {

  public static String getString(HttpURLConnection httpConn) throws IOException {
    String response;
    InputStream responseStream = new BufferedInputStream(httpConn.getInputStream());

    BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(responseStream, UTF_8));

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

  public static Object getErrorContent(HttpURLConnection httpConn) throws IOException {
    BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getErrorStream()));
    StringBuilder response = new StringBuilder();
    String currentLine;

    while ((currentLine = in.readLine()) != null)
      response.append(currentLine);

    in.close();

    String responseString = response.toString();
    try{
      return new GsonBuilder().create().fromJson(responseString, Document.class).get("data");
    }
    catch(Exception e){
      return responseString;
    }
  }

}
