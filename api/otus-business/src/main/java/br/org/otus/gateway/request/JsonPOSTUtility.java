package br.org.otus.gateway.request;

import br.org.otus.gateway.response.exception.RequestException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JsonPOSTUtility {
    private HttpURLConnection httpConn;
    private DataOutputStream request;

    public JsonPOSTUtility(URL requestURL, String body) throws IOException {
        httpConn = (HttpURLConnection) requestURL.openConnection();
        httpConn.setUseCaches(false);
        httpConn.setDoOutput(true);
        httpConn.setDoInput(true);

        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("Connection", "Keep-Alive");
        httpConn.setRequestProperty("Cache-Control", "no-cache");
        httpConn.setRequestProperty("Content-Type", "application/json");
        request = new DataOutputStream(httpConn.getOutputStream());
        request.write(body.getBytes(StandardCharsets.UTF_8));
    }

    public String finish() throws IOException, RequestException {
        String response;

        request.flush();
        request.close();
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            response = RequestUtility.getString(httpConn);
        } else {
            throw new RequestException(status);
        }

        return response;
    }

}
