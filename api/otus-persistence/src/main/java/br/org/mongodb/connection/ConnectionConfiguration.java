package br.org.mongodb.connection;

import java.io.InputStream;
import java.util.Properties;

public abstract class ConnectionConfiguration {

    protected static final String DATABASE_PROPERTIES = "/META-INF/database.properties";
    protected String username;
    protected String password;
    protected String database;
    protected String host;
    protected int port;

    public ConnectionConfiguration() {
        Properties properties = this.read();
        username = properties.getProperty("mongodb.username");
        password = properties.getProperty("mongodb.password");
        database = properties.getProperty("mongodb.database");
        host = properties.getProperty("mongodb.host");
        port = Integer.parseInt(properties.getProperty("mongodb.port"));
    }

    public Properties read() {
        try {
            Properties properties = new Properties();
            InputStream resourceAsStream = ConnectionConfiguration.class.getClassLoader().getResourceAsStream(DATABASE_PROPERTIES);
            properties.load(resourceAsStream);
            return properties;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
