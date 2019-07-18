package br.org.otus.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

public class GatewayFacade {

    public GatewayResponse findCurrentVariables(String body) throws MalformedURLException {
        String requestType = "POST";
        GatewayResponse response = new GatewayResponse();
        return response.buildSuccess(readRequest(new GatewayService().microserviceConnection(requestType, body)));
    }

    private String readRequest(HttpURLConnection microserviceConnection) throws ReadRequestException {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    microserviceConnection.getInputStream()));
            return br.readLine();
        } catch (IOException e) {
            throw new ReadRequestException();
        }
    }

}




