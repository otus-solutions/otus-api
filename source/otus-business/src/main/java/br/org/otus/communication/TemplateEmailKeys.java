package br.org.otus.communication;

public enum TemplateEmailKeys {
  RESET_PASSWD_PARTICIPANT("5ea88862ae51d800083aeba7"),
  ACTIVITY_SENDING("5e1e2ef297369acec0f34429"),
  NEW_USER_GREETINGS("5fa61989bbc9508293069558"),
  NEW_USER_NOTIFICATION("5fa639e8a3809f16a828d6e5");

  private final String value;

  TemplateEmailKeys(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
