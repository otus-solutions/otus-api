package br.org.otus.security.dtos;

import br.org.otus.user.dto.FieldCenterDTO;
import br.org.tutty.Equalization;
import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.participant.model.Sex;
import org.ccem.otus.survey.template.utils.date.ImmutableDate;

import java.util.ArrayList;

public class ParticipantSecurityAuthorizationDto implements Dto {

  @Equalization(name = "recruitmentNumber")
  private Long recruitmentNumber;

  @Equalization(name = "name")
  private String name;

  @Equalization(name = "sex")
  private Sex sex;

  @Equalization(name = "birthdate")
  private ImmutableDate birthdate;

  private FieldCenterDTO fieldCenter;

  @Equalization(name = "email")
  private String email;

  private String token;

  @Override
  public Boolean isValid() {
    return Boolean.TRUE;
  }

  public FieldCenterDTO getFieldCenter() {
    return fieldCenter;
  }

  public void setFieldCenter(FieldCenterDTO fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
