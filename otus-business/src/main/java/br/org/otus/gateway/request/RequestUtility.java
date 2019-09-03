package br.org.otus.gateway.request;

import com.google.gson.GsonBuilder;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.HttpURLConnection;

public class RequestUtility {
    @NotNull
    static String getString(HttpURLConnection httpConn) throws IOException {
        String response;
        InputStream responseStream = new
                BufferedInputStream(httpConn.getInputStream());

        BufferedReader responseStreamReader =
                new BufferedReader(new InputStreamReader(responseStream));

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
