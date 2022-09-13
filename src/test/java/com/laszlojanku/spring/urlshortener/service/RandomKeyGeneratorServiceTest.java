package com.laszlojanku.spring.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomKeyGeneratorServiceTest {
	
	@InjectMocks
	private RandomKeyGeneratorService randomKeyGeneratorService;
	
	@Test
	public void isValid_WhenKeyContainsNumbers_ShouldReturnTrue() {
		String key = "1234";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	public void isValid_WhenKeyContainsLowerCase_ShouldReturnTrue() {
		String key = "abcd";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	public void isValid_WhenKeyContainsUpperCase_ShouldReturnTrue() {
		String key = "ABCD";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	public void isValid_WhenKeyContainsAlphaNums_ShouldReturnTrue() {
		String key = "a1B2";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}
	
	@Test
	public void isValid_WhenKeyIsEmpty_ShouldReturnFalse() {
		String key = "";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}
	
	@Test
	public void isValid_WhenKeyNotInTheSeed_ShouldReturnFalse() {
		String key = "a1B-";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}
	
	@Test
	public void isValid_WhenNull_ShouldReturnFalse() {
		String key = null;
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertFalse(actual);
	}

}