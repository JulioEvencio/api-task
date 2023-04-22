package com.github.julioevencio.apitask.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		Info info = new Info()
				.title("API Task")
				.version("1.0")
				.description("This project is a REST API of a task manager")
				.termsOfService("https://github.com/JulioEvencio/api-task/blob/main/LICENSE")
				.license(new License().name("MIT License")
						.url("https://github.com/JulioEvencio/api-task/blob/main/LICENSE"));
		
		return new OpenAPI().info(info);
	}

}
