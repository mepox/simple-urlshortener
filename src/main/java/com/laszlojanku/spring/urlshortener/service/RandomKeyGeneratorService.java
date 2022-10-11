package com.laszlojanku.spring.urlshortener.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laszlojanku.spring.urlshortener.repository.JdbcTinyURLRepository;

/**
 * Generate a random key from characters [a-z, A-Z, 0-9].
 */
@Service
public class RandomKeyGeneratorService implements KeyGeneratorService {
	
	private final String SEED = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"; // 62
	private final int KEY_LENGTH = 4; // Length of the key	
	
	private JdbcTinyURLRepository repository;
	private Random rand;
	
	@Autowired
	public RandomKeyGeneratorService(JdbcTinyURLRepository repository) {
		this.repository = repository;
		rand = new Random();
	}

	@Override
	public String getKey() {
		return generate();
	}
	
	private String generate() {
		String key = "";
		
		do {
			key = getRandomKey(KEY_LENGTH);
		} while (repository.isExists(key));
		
		return key;
	}
	
	private String getRandomKey(int length) {
		StringBuilder sb = new StringBuilder();		
		
		for (int i = 0; i < length; i++) {
			int index = rand.nextInt(62);
			sb.append(SEED.charAt(index));
		}
		
		return sb.toString();
	}

	@Override
	public boolean isValid(String strKey) {
		if (strKey == null || strKey.isBlank()) {
			return false;
		}
		
		if (strKey.length() != KEY_LENGTH) {
			return false;
		}
		
		boolean found = false;
		for (int i = 0; i < strKey.length(); i++) {
			found = false;
			for (int j = 0; j < SEED.length(); j++) {
				if (strKey.charAt(i) == SEED.charAt(j)) {
					found = true;
					break;
				}
			}
			if (!found) {
				break;
			}
		}
		
		return found;
	}

}