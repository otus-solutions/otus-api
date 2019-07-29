package br.org.otus.gateway;

import br.org.otus.gateway.response.RequestException;
import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GatewayService {

    public HttpURLConnection microserviceConnection(String requestType, String body) throws MalformedURLException {
        return connect(requestType,body);
    }

    private HttpURLConnection connect(String requestType, String body) throws MalformedURLException {
        URL url = new DBDistributionMicroServiceResources().getAddressPostVariablesResource();
        HttpURLConnection microserviceConnection = request(url, requestType, body);
        requestValidator(microserviceConnection);
        return microserviceConnection;
    }

    private HttpURLConnection request(URL url, String typeReq, String body) {
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestMethod(typeReq);
            conn.setDoOutput(true);
            conn.getOutputStream().write(body.getBytes("UTF-8"));
            return conn;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void requestValidator(HttpURLConnection microserviceConnection) {
        int responseCode = 200;

        try {
            responseCode = microserviceConnection.getResponseCode();
            if ( responseCode != 200) {
                throw new RuntimeException("Failed: HTTP error code:"
                        + microserviceConnection.getResponseCode());
            }
        } catch (IOException e) {
            throw new RequestException(9);
        }
    }












}
