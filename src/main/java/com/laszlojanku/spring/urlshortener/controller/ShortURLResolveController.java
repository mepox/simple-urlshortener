package com.laszlojanku.spring.urlshortener.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.spring.urlshortener.service.ShortURLService;

/** 
 * REST Controller to resolve a ShortURL by key.
 */

@RestController
public class ShortURLResolveController {
	
	@Autowired
	private ShortURLService shortURLService;
	
	/**
	 * Resolves a ShortURL using the key.
	 * @param	key	the key of the ShortURL
	 * @return		returns the original url or an error message
	 */	
	@GetMapping("/u/{key}")
	public ResponseEntity<String> getURL(@PathVariable("key") String strKey) {
		String url = "";
		
		try {
			url = shortURLService.getURL(strKey);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		HttpHeaders headers = new HttpHeaders();		
		headers.setLocation(URI.create(url));
		return new ResponseEntity<String>(headers, HttpStatus.MOVED_PERMANENTLY);	
	}

}