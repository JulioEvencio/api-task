package com.github.julioevencio.apitask.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.github.julioevencio.apitask.services.JwtTokenProviderService;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private JwtTokenProviderService jwtTokenProviderService;

	public JwtConfigurer(JwtTokenProviderService jwtTokenProviderService) {
		this.jwtTokenProviderService = jwtTokenProviderService;
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		JwtTokenFilter customFilter = new JwtTokenFilter(jwtTokenProviderService);

		http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
