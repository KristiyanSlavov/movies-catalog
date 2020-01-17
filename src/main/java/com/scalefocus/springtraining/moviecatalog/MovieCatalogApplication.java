package com.scalefocus.springtraining.moviecatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
public class MovieCatalogApplication {


	public static void main(String[] args) throws Exception{

		SpringApplication.run(MovieCatalogApplication.class, args);

	}

}
