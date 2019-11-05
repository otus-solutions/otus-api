package br.org.mongodb.connection;

public abstract class ConnectionConfiguration {

  protected static final String DATABASE_NAME = "DATABASE_NAME";
  protected static final String DATABASE_HOST = "DATABASE_HOST";
  protected static final String DATABASE_PORT = "DATABASE_PORT";
  protected static final String DATABASE_USER = "DATABASE_USER";
  protected static final String DATABASE_PASS = "DATABASE_PASS";

  protected String username;
  protected String password;
  protected String database;
  protected String host;
  protected int port;

  public ConnectionConfiguration() {
    database = System.getenv(DATABASE_NAME);
    username = System.getenv(DATABASE_USER);
    password = System.getenv(DATABASE_PASS);
    host = System.getenv(DATABASE_HOST);
    port = Integer.parseInt(System.getenv(DATABASE_PORT));
  }
}
