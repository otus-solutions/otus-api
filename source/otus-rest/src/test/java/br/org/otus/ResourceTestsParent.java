package br.org.otus;

public abstract class ResourceTestsParent {

  protected final static String EMPTY_RESPONSE = "{\"data\":true}";

  protected String encapsulateExpectedResponse(String data) {
    return "{\"data\":" + data + "}";
  }
}
