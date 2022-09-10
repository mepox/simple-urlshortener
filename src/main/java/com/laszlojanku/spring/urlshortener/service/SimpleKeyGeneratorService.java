package com.laszlojanku.spring.urlshortener.service;

import org.springframework.stereotype.Service;

/** 	
 *	A Simple Key Generator that generates a key, starting from 1000 to be used as a key for the TinyURL. 
 */

@Service
public class SimpleKeyGeneratorService implements KeyGeneratorService {
	
	private int key = 1000;

	@Override
	public String getKey() {				
		String newKey = Integer.toString(key);
		key++;
		return newKey;
	}

	@Override
	public boolean isValid(String strKey) {
		int key;
		
		try {
			key = Integer.parseInt(strKey);
		} catch (NumberFormatException e) {
			return false;
		}
		
		if (key < 1000) {
			return false;
		}
		
		return true;
	}

}