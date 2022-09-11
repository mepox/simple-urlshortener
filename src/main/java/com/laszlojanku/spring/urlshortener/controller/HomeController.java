package com.laszlojanku.spring.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.laszlojanku.spring.urlshortener.exception.JdbcException;
import com.laszlojanku.spring.urlshortener.exception.KeyNotFoundException;
import com.laszlojanku.spring.urlshortener.exception.KeyNotValidException;
import com.laszlojanku.spring.urlshortener.exception.UrlNotValidException;
import com.laszlojanku.spring.urlshortener.service.TinyURLService;

/**
 * Responsible to handle the main page's REST API requests.
 */

@RestController
public class HomeController {
	
	@Autowired
	private TinyURLService tinyURLService;
	
	/**
	 * Handles the client POST requests to add a new url.	
	 * @param url	the new url to be added
	 * @return 		the key for the new url or an error message
	 */
	@PostMapping("/urls/add")	
	public ResponseEntity<String> addURL(@RequestBody String url) {
		String key = "";
		try {
			key = tinyURLService.addURL(url);
		} catch (UrlNotValidException | JdbcException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);			
		}		
		
		return new ResponseEntity<String>(key, HttpStatus.OK);		
	}
	
	/**
	 * Handles the client DELETE requests to delete an url.
	 * @param key	the key of the url to be deleted
	 * @return		a status message with the result   
	 */	
	@DeleteMapping("/urls/delete/{key}")	
	public ResponseEntity<String> deleteURL(@PathVariable("key") String key) {
		try {
			tinyURLService.deleteURL(key);
		}
		catch (KeyNotValidException | KeyNotFoundException | JdbcException e) {			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Deleted the URL successfully.", HttpStatus.OK);
	}
	
	/**
	 * Handles the client GET requests to get all the TinyURL.
	 * 
	 * @return	a list of all the TinyURL
	 */
	@GetMapping("/urls/all")
	public ResponseEntity<String> getAllURLs() {
		String result = "";
		
		try {
			result = tinyURLService.getAll().toString();
		}
		catch (JdbcException e) {			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);		
	}
}