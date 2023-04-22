package com.github.julioevencio.apitask.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.github.julioevencio.apitask.services.TokenJwtService;
import com.github.julioevencio.apitask.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final TokenJwtService tokenJwtService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;

	public SecurityConfig(TokenJwtService tokenJwtService, UserDetailsServiceImpl userDetailsServiceImpl) {
		this.tokenJwtService = tokenJwtService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(tokenJwtService, userDetailsServiceImpl);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
				.httpBasic().disable()
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeHttpRequests()
					.requestMatchers(HttpMethod.POST, "/auth/login", "/auth/register").permitAll()
					.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/auth/me").authenticated()
					.requestMatchers("/api/**").authenticated()
					.anyRequest().denyAll()
				.and()
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
				.build();
	}

}
