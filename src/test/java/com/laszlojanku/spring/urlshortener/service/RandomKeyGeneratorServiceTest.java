package com.laszlojanku.spring.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RandomKeyGeneratorServiceTest {
	
	@InjectMocks
	private RandomKeyGeneratorService randomKeyGeneratorService;
	
	@Test
	void isValid_WhenKeyContainsNumbers_ShouldReturnTrue() {
		String key = "1234";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	void isValid_WhenKeyContainsLowerCase_ShouldReturnTrue() {
		String key = "abcd";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	void isValid_WhenKeyContainsUpperCase_ShouldReturnTrue() {
		String key = "ABCD";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	void isValid_WhenKeyContainsAlphaNums_ShouldReturnTrue() {
		String key = "a1B2";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	void isValid_WhenKeyIsEmpty_ShouldReturnFalse() {
		String key = "";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}
	
	@Test
	void isValid_WhenKeyNotInTheSeed_ShouldReturnFalse() {
		String key = "a1B-";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}
	
	@Test
	void isValid_WhenNull_ShouldReturnFalse() {
		String key = null;
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}

}