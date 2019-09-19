package br.org.otus.email.user.management;

import br.org.otus.email.OtusEmail;
import br.org.otus.model.User;
import br.org.owail.sender.email.Email;
import br.org.owail.sender.email.Mailer;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DisableUserNotificationEmail extends Email implements OtusEmail {

  private final String TEMPLATE = "/template/user/management/disable-user-notification-template.html";
  private final String SUBJECT = "Alerta - Cadastro desabilitado Otus";
  private Map<String, String> dataMap;

  public DisableUserNotificationEmail() {
    dataMap = new HashMap<>();
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

  public void defineRecipient(User user) {
    addTORecipient("recipient", user.getEmail());
  }

  private void defineSubject() {
    setSubject(SUBJECT);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    DisableUserNotificationEmail that = (DisableUserNotificationEmail) o;
    return Objects.equals(TEMPLATE, that.TEMPLATE) &&
            Objects.equals(SUBJECT, that.SUBJECT) &&
            Objects.equals(dataMap, that.dataMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(TEMPLATE, SUBJECT, dataMap);
  }
}
