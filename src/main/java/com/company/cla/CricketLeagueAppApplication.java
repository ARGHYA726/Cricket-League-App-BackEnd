package com.company.cla;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CricketLeagueAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CricketLeagueAppApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {

		return new Docket(DocumentationType.SWAGGER_2).select().paths(PathSelectors.any())
				.apis(RequestHandlerSelectors.basePackage("com.company.cla.controller")).build().apiInfo(apiDetails());

	}

	public ApiInfo apiDetails() {
		return new ApiInfo("Cricket League Application", "Manages all possible details of cricket league", "1.0", null,
				null, "API Liscence", null, Collections.emptyList());
	}
}
