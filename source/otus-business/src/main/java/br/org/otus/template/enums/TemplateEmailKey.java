package br.org.otus.template.enums;

public enum TemplateEmailKey {
  TEMPLATE_RESET_PASSWD_PARTICIPANT_ID("5ea88862ae51d800083aeba7");

  private String value;

  TemplateEmailKey(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
