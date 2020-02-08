package br.org.otus.security.dtos;

import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import br.org.otus.user.dto.FieldCenterDTO;
import br.org.tutty.Equalization;

public class UserSecurityAuthorizationDto implements Dto {

  @Equalization(name = "name")
  private String name;

  @Equalization(name = "surname")
  private String surname;

  private FieldCenterDTO fieldCenter;

  @Equalization(name = "phone")
  private String phone;

  @Equalization(name = "email")
  private String email;

  @Equalization(name = "token")
  private String token;

  @Equalization(name = "code")
  private Integer code;

  @Override
  public Boolean isValid() {
    return Boolean.TRUE;
  }

  public UserSecurityAuthorizationDto() {
    this.fieldCenter = new FieldCenterDTO();
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
