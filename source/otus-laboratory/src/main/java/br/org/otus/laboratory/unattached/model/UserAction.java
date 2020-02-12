package br.org.otus.laboratory.unattached.model;

import br.org.otus.laboratory.unattached.enums.UnattachedLaboratoryActions;

import java.time.LocalDateTime;

public class UserAction {
  private UnattachedLaboratoryActions action;
  private LocalDateTime date;
  private String userEmail;

  public UserAction(UnattachedLaboratoryActions action, String userEmail) {
    this.date = LocalDateTime.now();
    this.action = action;
    this.userEmail = userEmail;
  }
}
