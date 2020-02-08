package br.org.otus.survey.dtos;

import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;
import org.ccem.otus.survey.form.SurveyFormType;

public class UpdateSurveyFormTypeDto implements Dto {

  public String acronym;
  public SurveyFormType newSurveyFormType;

  @Override
  public Boolean isValid() {
    return (acronym != null && !acronym.isEmpty() &&
      newSurveyFormType != null);
  }
}
