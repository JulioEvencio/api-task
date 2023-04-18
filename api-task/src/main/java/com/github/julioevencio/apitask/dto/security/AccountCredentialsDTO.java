package com.github.julioevencio.apitask.dto.security;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class AccountCredentialsDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Invalid username")
	@Length(max = 100, min = 1, message = "The e-mail must be between 1 and 20 characters long")
	private String username;

	@NotBlank(message = "Invalid password")
	private String password;

	public AccountCredentialsDTO(
			@NotBlank(message = "Invalid username") @Length(max = 100, min = 1, message = "The e-mail must be between 1 and 100 characters long") String username,
			@NotBlank(message = "Invalid password") String password) {
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

}
