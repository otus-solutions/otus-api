package br.org.otus.gateway;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GatewayFacade {

    private static final String HOST = "http://localhost:";
    private static final String PORT = "8083";
    private static final String END_POINT = "/api/findAllCurrentVariables";


    public String getCurrentVariables() throws IOException {

            URL url = new URL(HOST + PORT + END_POINT);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            if(conn.getResponseCode() != 200){
                throw new RuntimeException("Failed: HTTP error code:"
                + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));

            String currentVariables =  br.readLine();
            conn.disconnect();

        return currentVariables;

    }

}




