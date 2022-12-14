package com.laszlojanku.spring.urlshortener.service;

/**
 * Interface for the KeyGeneratorService(s)
 */
public interface KeyGeneratorService {
	
	public String getKey();
	public boolean isValid(String strKey);
}