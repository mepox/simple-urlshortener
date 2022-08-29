package com.laszlojanku.spring.urlshortener.service;

import org.springframework.stereotype.Service;

/** 	
 *	A Simple Key Generator that generates a key, starting from 1000 to be used as a key for the ShortURL. 
 */

@Service
public class SimpleKeyGeneratorService implements KeyGeneratorService {
	
	private int key = 1000;

	@Override
	public int getKey() {				
		return key++;
	}

}