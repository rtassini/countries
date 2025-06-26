package com.itau.countries;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Countries", version = "0.0.1-SNAPSHOT"))
public class CountriesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CountriesApplication.class, args);
	}

}
