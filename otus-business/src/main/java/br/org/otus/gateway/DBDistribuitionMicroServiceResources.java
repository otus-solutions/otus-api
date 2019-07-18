package br.org.otus.gateway;

import java.net.MalformedURLException;
import java.net.URL;

public class DBDistribuitionMicroServiceResources extends MicroservicesResources{
    private static final String POST_VARIABLES_RESOURCE = "/api/findCurrentVariables";


    public DBDistribuitionMicroServiceResources() {
        super(MicroservicesEnvironments.DBDISTRIBUITION);
    }

    public URL getAddressPostVariablesResource() throws MalformedURLException {
        return new URL(this.HOST+ this.PORT + POST_VARIABLES_RESOURCE);
    }
}
