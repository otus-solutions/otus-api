package org.ccem.otus.model.survey.activity.user;

public class ActivityBasicUser {
	
	private String name;
	private String surname;
	private String phone;
	private String email;

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNameAndSurname(String name,String surname){
		this.name = name + " " + surname;
	}

	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	
}
