package com.laszlojanku.spring.urlshortener.exception;

public class UrlNotValidException extends RuntimeException {
	
	public UrlNotValidException(String message) {
		super(message);
	}

}