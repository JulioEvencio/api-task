package com.github.julioevencio.apitask.dto.security;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AccountCredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Email(message = "Invalid e-mail")
	private String email;

	@NotBlank(message = "Invalid password")
	private String password;

	public AccountCredentialsDTO(@Email(message = "Invalid e-mail") String email,
			@NotBlank(message = "Invalid password") String password) {
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
