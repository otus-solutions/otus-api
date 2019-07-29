package br.org.otus.gateway.resource;

import br.org.otus.gateway.MicroservicesEnvironments;

import java.net.MalformedURLException;
import java.net.URL;

public class DBDistributionMicroServiceResources extends MicroservicesResources{
    private static final String FIND_VARIABLES_RESOURCE = "/api/findVariables";
    private static final String DATABASE_UPLOAD_RESOURCE = "/api/upload/database";


    public DBDistributionMicroServiceResources() {
        super(MicroservicesEnvironments.DBDISTRIBUTION);
    }

    public URL getAddressPostVariablesResource() throws MalformedURLException {
        return new URL(this.HOST+ this.PORT + FIND_VARIABLES_RESOURCE);
    }

    public URL getAddressDatabaseUpload() throws MalformedURLException {
        return new URL(this.HOST+ this.PORT + FIND_VARIABLES_RESOURCE);
    }
}
