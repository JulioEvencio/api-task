package com.github.julioevencio.apitask.dto.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Invalid username")
	@Length(max = 20, min = 1, message = "The username must be between 1 and 20 characters long")
	private String username;

	@Email(message = "Invalid e-mail")
	@Length(max = 100, min = 1, message = "The e-mail must be between 1 and 100 characters long")
	private String email;

	@NotBlank(message = "Invalid password")
	@Length(max = 20, min = 1, message = "The password must be between 1 and 20 characters long")
	private String password;

	public UserRequestDTO(
			@NotBlank(message = "Invalid username") @Length(max = 20, min = 1, message = "The username must be between 1 and 20 characters long") String username,
			@Email(message = "Invalid e-mail") @Length(max = 100, min = 1, message = "The e-mail must be between 1 and 100 characters long") String email,
			@NotBlank(message = "Invalid password") @Length(max = 20, min = 1, message = "The password must be between 1 and 20 characters long") String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
