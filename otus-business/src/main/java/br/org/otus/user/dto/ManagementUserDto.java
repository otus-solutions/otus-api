package br.org.otus.user.dto;

import org.ccem.otus.exceptions.Dto;
import org.ccem.otus.exceptions.webservice.security.EncryptedException;

import br.org.tutty.Equalization;

import java.util.ArrayList;

public class ManagementUserDto implements Dto {
    @Equalization(name = "name")
    public String name;

    @Equalization(name = "surname")
    public String surname;

    @Equalization(name = "extraction")
    public Boolean extraction;

    @Equalization(name = "extraction_ips")
    public ArrayList extractionIps;

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

    @Override
    public Boolean isValid() {
        return !name.isEmpty() &&
                !surname.isEmpty() &&
                !phone.isEmpty() &&
                !email.isEmpty()
                ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void encrypt() throws EncryptedException {
    }
}
