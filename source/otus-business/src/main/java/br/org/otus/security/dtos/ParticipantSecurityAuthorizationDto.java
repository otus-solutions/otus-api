package br.org.otus.security.dtos;

import br.org.otus.user.dto.FieldCenterDTO;
import br.org.tutty.Equalization;
import org.ccem.otus.exceptions.Dto;

import java.util.ArrayList;

public class ParticipantSecurityAuthorizationDto implements Dto {

  @Equalization(name = "recruitmentNumber")
  private String recruitmentNumber;

  @Equalization(name = "name")
  private String name;

  @Equalization(name = "sex")
  private String sex;

  @Equalization(name = "birthdate")
  private String birthdate;

  private FieldCenterDTO fieldCenter;

  @Equalization(name = "email")
  private String email;

  @Equalization(name = "password")
  private String password;

  @Equalization(name = "password")
  private ArrayList<String> tokenList;

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

  public void addToken(String token) {
    this.tokenList.add(token);
  }
}
