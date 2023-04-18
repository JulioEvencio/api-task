package com.github.julioevencio.apitask.dto.security;

import java.io.Serializable;
import java.util.Date;

public class TokenDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private Boolean authenticated;
	private Date created;
	private Date expiration;
	private String accessToken;
	private String refreshToken;

	public TokenDTO(String username, Boolean authenticated, Date created, Date expiration, String accessToken,
			String refreshToken) {
		this.username = username;
		this.authenticated = authenticated;
		this.created = created;
		this.expiration = expiration;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public Boolean getAuthenticated() {
		return authenticated;
	}

	public Date getCreated() {
		return created;
	}

	public Date getExpiration() {
		return expiration;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

}
