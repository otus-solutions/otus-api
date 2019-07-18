package br.org.otus.gateway;

public enum MicroservicesEnvironments {
    DBDISTRIBUITION("DBDISTRIBUITION_HOST", "DBDISTRIBUITION_PORT");

    private String host;
    private String port;

    MicroservicesEnvironments(String host, String port){
        this.host = host;
        this.port = port;
    }

    public String getHost(){
        return host;
    }

    public String getPort(){
        return port;
    }

}
