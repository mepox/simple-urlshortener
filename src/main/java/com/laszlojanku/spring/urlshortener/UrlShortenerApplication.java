package com.laszlojanku.spring.urlshortener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UrlShortenerApplication {
	
	public static Logger logger = LoggerFactory.getLogger(UrlShortenerApplication.class);	

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerApplication.class, args);
	}

}