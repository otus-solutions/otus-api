package br.org.otus.gateway;


import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GatewayFacade {

    private static final String HOST = "http://localhost:";
    private static final String PORT = "8083";
    private static final String GET_VARIABLES_RESOURCE = "/api/findAllCurrentVariables";
    private static final String POST_VARIABLES_RESOURCE = "/api/findCurrentVariables";
    private static final String  TYPE_REQ_GET = "GET";
    private static final String  TYPE_REQ_POST = "POST";



//    public String getCurrentVariables() throws IOException {
//        HttpURLConnection microserviceConnection = connectorPerTypeOfRequest(HOST, PORT, GET_VARIABLES_RESOURCE, TYPE_REQ);
//        requisitionValidator(microserviceConnection);
//        String currentVariables = extractionCurrentVariables(microserviceConnection);
//        microserviceConnection.disconnect();
//        return currentVariables;
//    }

    public String findCurrentVariables(String infoVariables) throws IOException {
        HttpURLConnection microserviceConnection = connectorPerTypeOfRequest(HOST, PORT, POST_VARIABLES_RESOURCE, TYPE_REQ_POST, infoVariables);
        requisitionValidator(microserviceConnection);
        String currentVariables = extractionCurrentVariables(microserviceConnection);
        microserviceConnection.disconnect();
        return currentVariables;
    }

    private HttpURLConnection connectorPerTypeOfRequest(String host, String port, String resource, String typeReq, String infoVariables) throws IOException {
        URL url = new URL(host + port + resource);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(typeReq);
        conn.setDoOutput(true);
        conn.getOutputStream().write(infoVariables.getBytes("UTF-8"));
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




