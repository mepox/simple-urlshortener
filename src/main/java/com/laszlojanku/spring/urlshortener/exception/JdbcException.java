package com.laszlojanku.spring.urlshortener.exception;

public class JdbcException extends RuntimeException {
	
	public JdbcException(String message) {
		super(message);
	}

}