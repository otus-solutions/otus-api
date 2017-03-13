package br.org.otus.model;

import br.org.tutty.Equalization;
import com.google.gson.GsonBuilder;

import java.util.UUID;

public class User {

	@Equalization(name = "uuid")
	private UUID uuid;

	@Equalization(name = "admin_flag")
	private Boolean adm;

	@Equalization(name = "enable")
	private Boolean enable;

	@Equalization(name = "name")
	private String name;

	@Equalization(name = "surname")
	private String surname;

	@Equalization(name = "phone")
	private String phone;

	@Equalization(name = "email")
	private String email;

	@Equalization(name = "password")
	private String password;

	@Equalization(name = "code")
	private Integer code;

	public User() {
		this.uuid = UUID.randomUUID();
		this.adm = Boolean.FALSE;
		this.enable = Boolean.FALSE;
	}

	public void enable() {
		this.enable = Boolean.TRUE;
	}

	public void disable() {
		this.enable = Boolean.FALSE;
	}

	public void becomesAdm() {
		this.adm = Boolean.TRUE;
		enable();
	}

	public Boolean isAdmin(){
		return adm;
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

	public String getPassword() {
		return password;
	}

	public Boolean isEnable() {
		return enable;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public static User deserialize(String user) {
		GsonBuilder builder = new GsonBuilder();
		return builder.create().fromJson(user, User.class);
	}

	public static String serialize(User user) {
		return new GsonBuilder().create().toJson(user);
	}
}
