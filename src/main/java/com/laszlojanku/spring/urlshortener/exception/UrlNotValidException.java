package com.laszlojanku.spring.urlshortener.exception;

public class UrlNotValidException extends Exception {
	
	public UrlNotValidException(String message) {
		super(message);
	}

}