package br.org.otus.gateway;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GatewayFacade {

    private static final String HOST = "http://localhost:";
    private static final String PORT = "8083";
    private static final String LIST_VARIABLES_RESOURCE = "/api/findAllCurrentVariables";


    public String getCurrentVariables() throws IOException {
        HttpURLConnection microserviceConnection = connectorPerTypeOfRequest(HOST, PORT, LIST_VARIABLES_RESOURCE, "GET");
        requisitionValidator(microserviceConnection);
        String currentVariables = extractionCurrentVariables(microserviceConnection);
        microserviceConnection.disconnect();
        return currentVariables;
    }

    private HttpURLConnection connectorPerTypeOfRequest(String host, String port, String resource, String typeReq) throws IOException {
        URL url = new URL(host + port + resource);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(typeReq);
        return conn;
    }

    private void requisitionValidator(HttpURLConnection microserviceConnection) throws IOException {
        if (microserviceConnection.getResponseCode() != 200) {
            throw new RuntimeException("Failed: HTTP error code:"
                    + microserviceConnection.getResponseCode());
        }
    }

    private String extractionCurrentVariables(HttpURLConnection microserviceConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(
                microserviceConnection.getInputStream()));
        return br.readLine();
    }
}




