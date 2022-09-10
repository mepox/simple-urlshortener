package com.laszlojanku.spring.urlshortener.exception;

public class UrlNotFoundException extends RuntimeException {
	
	public UrlNotFoundException(String message) {
		super(message);
	}

}