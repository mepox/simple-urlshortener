package com.laszlojanku.spring.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

/**
 * Validate an URL. 
 */
@Service
public class UrlValidatorService {
	
	/**
	 * Validates an URL using java.net.URL
	 * @param url	the url that needs to be validated
	 * @return		true if the url is valid
	 */
	public boolean isValid(String url) {
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		
		return true;
	}

}