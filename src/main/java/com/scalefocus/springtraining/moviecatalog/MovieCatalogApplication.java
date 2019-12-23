package com.scalefocus.springtraining.moviecatalog;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieCatalogApplication {

	private static final Log log = LogFactory.getLog(MovieCatalogApplication.class);

	public static void main(String[] args) throws Exception{

		SpringApplication.run(MovieCatalogApplication.class, args);
	}

}
