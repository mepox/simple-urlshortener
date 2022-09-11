package com.laszlojanku.spring.urlshortener.exception;

public class UrlNotFoundException extends Exception {
	
	public UrlNotFoundException(String message) {
		super(message);
	}

}