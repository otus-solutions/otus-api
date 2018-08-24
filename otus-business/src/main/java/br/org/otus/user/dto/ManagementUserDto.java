package br.org.otus.user.dto;

import java.util.ArrayList;
import java.util.List;

import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import br.org.tutty.Equalization;

public class ManagementUserDto implements Dto {
	@Equalization(name = "name")
	public String name;

	@Equalization(name = "surname")
	public String surname;

	@Equalization(name = "extraction")
	public Boolean extraction;

	@Equalization(name = "extraction_ips")
	public List extractionIps;

	@Equalization(name = "phone")
	public String phone;

	public FieldCenterDTO fieldCenter;

	@Equalization(name = "email")
	public String email;

	@Equalization(name = "admin_flag")
	public Boolean admin;

	@Equalization(name = "enable")
	public Boolean enable;

	public ManagementUserDto() {
		this.fieldCenter = new FieldCenterDTO();
	}

	public String getEmail() {
		return email;
	}

	public List<String> getExtractionIps() {
		return extractionIps;
	}

	@Override
	public Boolean isValid() {
		return (name != null && !name.isEmpty()) && (surname != null && !surname.isEmpty()) && (phone != null && !phone.isEmpty()) && (email != null && !email.isEmpty()) ? Boolean.TRUE : Boolean.FALSE;
	}

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public Boolean getExtraction() {
    return extraction;
  }

  public void setExtraction(Boolean extraction) {
    this.extraction = extraction;
  }

  public void setExtractionIps(List extractionIps) {
    this.extractionIps = extractionIps;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public FieldCenterDTO getFieldCenter() {
    return fieldCenter;
  }

  public void setFieldCenter(FieldCenterDTO fieldCenter) {
    this.fieldCenter = fieldCenter;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getAdmin() {
    return admin;
  }

  public void setAdmin(Boolean admin) {
    this.admin = admin;
  }

  public Boolean getEnable() {
    return enable;
  }

  public void setEnable(Boolean enable) {
    this.enable = enable;
  }
}
