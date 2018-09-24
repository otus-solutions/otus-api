package br.org.otus.email.user.management;

import br.org.otus.email.OtusEmail;
import br.org.owail.sender.email.Email;
import br.org.owail.sender.email.Mailer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PasswordResetEmail extends Email implements OtusEmail {

  private final String TEMPLATE = "/template/user/management/password-reset-notification-template.html";
  private final String SUBJECT = "[RECUPERAÇÃO DE ACESSO]";
  private Map<String, String> dataMap = new HashMap<>();

  public PasswordResetEmail(String token, String hostPath) {
    this.dataMap.put("token", token);
    this.dataMap.put("host", hostPath);
    defineSubject();
  }

  @Override
  public String getTemplatePath() {
    return TEMPLATE;
  }

  @Override
  public Map<String, String> getContentDataMap() {
    return dataMap;
  }

  @Override
  public String getContentType() {
    return Mailer.HTML;
  }

  public void defineRecipient(String email) {
    addTORecipient("recipient", email);
  }

  private void defineSubject() {
    setSubject(SUBJECT);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PasswordResetEmail that = (PasswordResetEmail) o;
    return Objects.equals(TEMPLATE, that.TEMPLATE) &&
            Objects.equals(SUBJECT, that.SUBJECT) &&
            Objects.equals(dataMap, that.dataMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(TEMPLATE, SUBJECT, dataMap);
  }
}
