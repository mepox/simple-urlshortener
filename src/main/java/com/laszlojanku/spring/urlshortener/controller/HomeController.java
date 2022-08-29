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

import com.laszlojanku.spring.urlshortener.service.ShortURLService;

/**
 * HomeController handles the main page's REST calls.
 */

@RestController
public class HomeController {
	
	@Autowired
	private ShortURLService shortURLService;
	
	/**
	 * Handles the client call to add a new url.	
	 * @param url	the new url to be added
	 * @return 		the key for the new url or an error message
	 */
	@PostMapping("/urls/add")	
	public ResponseEntity<String> addURL(@RequestBody String url) {
		int key = 0;
		try {
			key = shortURLService.addURL(url);
		} catch (Exception e) {			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(Integer.toString(key), HttpStatus.OK);		
	}
	
	/**
	 * Handles the client call to delete an url.
	 * @param key	the key of the url to be deleted
	 * @return		a status message with the result   
	 */	
	@DeleteMapping("/urls/delete/{key}")	
	public ResponseEntity<String> deleteURL(@PathVariable("key") String key) {
		try {
			shortURLService.deleteURL(key);
		}
		catch (Exception e) {			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>("Deleted the URL successfully.", HttpStatus.OK);
	}
	
	/**
	 * Handles the client call to get all the ShortURL.
	 * 
	 * @return	a list of all the ShortURL
	 */
	@GetMapping("/urls/all")
	public ResponseEntity<String> getAllURLs() {
		String result = "";
		
		try {
			result = shortURLService.getAll().toString();
		}
		catch (Exception e) {			
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<String>(result, HttpStatus.OK);		
	}
}