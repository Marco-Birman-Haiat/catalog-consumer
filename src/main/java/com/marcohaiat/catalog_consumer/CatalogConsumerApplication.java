package com.marcohaiat.catalog_consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
public class CatalogConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogConsumerApplication.class, args);
	}

}
