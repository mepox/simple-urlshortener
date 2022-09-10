package com.laszlojanku.spring.urlshortener.exception;

public class KeyNotValidException extends RuntimeException {
	
	public KeyNotValidException(String message) {
		super(message);
	}

}