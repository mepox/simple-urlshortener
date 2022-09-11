package com.laszlojanku.spring.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RandomKeyGeneratorServiceTest {
	
	@InjectMocks
	private RandomKeyGeneratorService randomKeyGeneratorService;
	
	@Test
	public void isValid_WhenValidKey_ShouldReturnTrue() {
		String key = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		
		boolean actual = randomKeyGeneratorService.isValid(key);
		
		assertTrue(actual);
	}

}