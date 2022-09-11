package com.laszlojanku.spring.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.laszlojanku.spring.urlshortener.exception.KeyNotValidException;
import com.laszlojanku.spring.urlshortener.exception.UrlNotFoundException;
import com.laszlojanku.spring.urlshortener.exception.UrlNotValidException;
import com.laszlojanku.spring.urlshortener.model.TinyURL;
import com.laszlojanku.spring.urlshortener.repository.JdbcTinyURLRepository;

@SpringBootTest
public class TinyURLServiceTest {
	
	@Mock
	private JdbcTinyURLRepository repository;
	
	@Mock
	private RandomKeyGeneratorService keyGeneratorService;
	
	@InjectMocks
	private TinyURLService tinyURLService;
	
	@Test
	public void addURL_WhenValid_ShouldntThrowException() {
		String validUrl = "http://www.google.com";
		
		assertDoesNotThrow(() -> tinyURLService.addURL(validUrl));		
	}
	
	@Test
	public void addURL_WhenInvalid_ShouldThrowException() {
		String invalidUrl = "www.google.com";
		
		assertThrows(UrlNotValidException.class, () -> tinyURLService.addURL(invalidUrl));
	}
	
	@Test
	public void getURL_WhenFound_ShouldntThrowException() throws Exception {
		String key = "123";
		
		when(keyGeneratorService.isValid(key)).thenReturn(true);
		when(repository.getTinyURL(key)).thenReturn(mockTinyURL(key, "http://www.google.com"));
		
		assertDoesNotThrow(() -> tinyURLService.getURL(key));
	}
	
	@Test
	public void getURL_WhenNotFound_ShouldThrowException() {	
		when(keyGeneratorService.isValid(anyString())).thenReturn(true);
		when(repository.getTinyURL(anyString())).thenReturn(null);
		
		assertThrows(UrlNotFoundException.class, () -> tinyURLService.getURL(anyString()));		
	}
	
	@Test
	public void getURL_WhenKeyNotValid_ShouldThrowException() {
		when(keyGeneratorService.isValid(anyString())).thenReturn(false);
		
		assertThrows(KeyNotValidException.class, () -> tinyURLService.getURL(anyString()));
	}
	
	private TinyURL mockTinyURL(String key, String url) {
		return new TinyURL(key, url);
	}

}