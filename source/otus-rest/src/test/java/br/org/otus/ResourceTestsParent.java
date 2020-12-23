package br.org.otus;

public abstract class ResourceTestsParent {

  protected final static String EMPTY_RESPONSE = "{\"data\":true}";
  protected final static int SUCCESS_STATUS = 200;

  protected String encapsulateExpectedResponse(String data) {
    return "{\"data\":" + data + "}";
  }

  protected String encapsulateExpectedStringResponse(String data) {
    return "{\"data\":\"" + data + "\"}";
  }
}
