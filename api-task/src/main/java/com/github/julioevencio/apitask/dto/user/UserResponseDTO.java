package com.github.julioevencio.apitask.dto.user;

import java.io.Serializable;

public class UserResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final String username;
	private final String email;

	public UserResponseDTO(Long id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

}
