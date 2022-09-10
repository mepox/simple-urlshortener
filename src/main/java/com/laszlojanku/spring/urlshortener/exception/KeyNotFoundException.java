package com.laszlojanku.spring.urlshortener.exception;

public class KeyNotFoundException extends RuntimeException {
	
	public KeyNotFoundException(String message) {
		super(message);
	}

}