package com.github.julioevencio.apitask.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.github.julioevencio.apitask.security.jwt.JwtConfigurer;
import com.github.julioevencio.apitask.services.JwtTokenProviderService;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	private JwtTokenProviderService jwtTokenProviderService;
	
	public SecurityConfig(JwtTokenProviderService jwtTokenProviderService) {
		this.jwtTokenProviderService = jwtTokenProviderService;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
				.authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
						.requestMatchers("/auth/signin", "/auth/create", "/auth/refresh/**").permitAll()
						.requestMatchers("/api/**").authenticated()
						.requestMatchers("/users").denyAll()
				)
				.cors()
				.and()
				.apply(new JwtConfigurer(jwtTokenProviderService))
				.and()
				.build();
	}
	
}
