package com.laszlojanku.spring.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UrlValidatorServiceTest {

	@InjectMocks
	private UrlValidatorService urlValidatorService;
	
	@Test
	void isValid_WhenValidUrl_ShouldReturnTrue() {
		String url = "http://www.google.com";
		
		boolean actual = urlValidatorService.isValid(url);
		
		assertTrue(actual);
	}
	
	@Test
	void isValid_WhenHttpIsMissing_ShouldReturnFalse() {
		String url = "www.google.com";
		
		boolean actual = urlValidatorService.isValid(url);
		
		assertFalse(actual);
	}
	
	@Test
	void isValid_WhenOneWord_ShouldReturnFalse() {
		String url = "google";
		
		boolean actual = urlValidatorService.isValid(url);
		
		assertFalse(actual);
	}
	
	@Test
	void isValid_WhenNull_ShouldReturnFalse() {
		String url = null;
		
		boolean actual = urlValidatorService.isValid(url);
		
		assertFalse(actual);
	}
	
	@Test
	void isValid_WhenEmpty_ShouldReturnFalse() {
		String url = "";
		
		boolean actual = urlValidatorService.isValid(url);
		
		assertFalse(actual);
	}
	
}