package br.org.otus.gateway;

import br.org.otus.gateway.resource.DBDistributionMicroServiceResources;
import br.org.otus.gateway.response.GatewayResponse;
import br.org.otus.gateway.response.ReadRequestException;
import org.ccem.otus.model.FileUploaderPOJO;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class GatewayFacade {

    public GatewayResponse findCurrentVariables(String body) throws MalformedURLException {
        String requestType = "POST";
        GatewayResponse response = new GatewayResponse();
        return response.buildSuccess(readRequest(new GatewayService().microserviceConnection(requestType, body)));
    }

    public GatewayResponse uploadDatabase(FileUploaderPOJO form) throws MalformedURLException {
        String charset = "UTF-8";
        URL requestURL = new DBDistributionMicroServiceResources().getAddressDatabaseUpload();

        try {
            String outputFile = "output.txt";
            Files.copy(form.getFile(), Paths.get(outputFile));
            File file = new File(outputFile);

            MultipartUtility multipart = new MultipartUtility(requestURL, charset);

            multipart.addFilePart("databaseJson", file);

            List<String> response = multipart.finish();

            return new GatewayResponse().buildSuccess(response);
        } catch (IOException ex) {
            throw new ReadRequestException();
        }
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




