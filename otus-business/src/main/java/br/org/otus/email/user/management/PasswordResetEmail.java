package br.org.otus.email.user.management;

import br.org.otus.email.OtusEmail;
import br.org.owail.sender.email.Email;
import br.org.owail.sender.email.Mailer;

import java.util.HashMap;
import java.util.Map;

public class PasswordResetEmail extends Email implements OtusEmail {

  //TODO 14/08/18: change template
  private final String TEMPLATE = "/template/user/management/enable-user-notification-template.html";
  private final String SUBJECT = "Pew!";
  private Map<String, String> dataMap;

  public PasswordResetEmail() {
    //TODO 14/08/18: why is this used for?
    this.dataMap = new HashMap<>();
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

}
