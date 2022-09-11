package com.laszlojanku.spring.urlshortener.service;

import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Service;

@Service
public class UrlValidatorService {
	
	public boolean isValid(String url) {
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			return false;
		}
		
		return true;
	}

}