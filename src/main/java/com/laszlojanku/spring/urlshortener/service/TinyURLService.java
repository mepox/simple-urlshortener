package com.laszlojanku.spring.urlshortener.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.laszlojanku.spring.urlshortener.exception.JdbcException;
import com.laszlojanku.spring.urlshortener.exception.KeyNotFoundException;
import com.laszlojanku.spring.urlshortener.exception.KeyNotValidException;
import com.laszlojanku.spring.urlshortener.exception.UrlNotFoundException;
import com.laszlojanku.spring.urlshortener.exception.UrlNotValidException;
import com.laszlojanku.spring.urlshortener.model.TinyURL;
import com.laszlojanku.spring.urlshortener.repository.JdbcTinyURLRepository;

/** 
 * Provides a Service to manipulate the TinyURLs.
 */
@Service
public class TinyURLService {
		
	@Autowired
	private JdbcTinyURLRepository repository;
	
	@Autowired
	private RandomKeyGeneratorService keyGeneratorService;
	
	@Autowired
	private UrlValidatorService urlValidatorService;
	
	/**
	 * Deletes the TinyURL from the database using the key.
	 * @param strKey	the key of the TinyURL in String	 
	 * @throws 			KeyNotFoundException 
	 * @throws 			KeyNotValidException
	 * @throws 			JdbcException
	 */	
	public void deleteURL(String strKey) throws KeyNotValidException, KeyNotFoundException, JdbcException {	
		if (!keyGeneratorService.isValid(strKey)) {
			throw new KeyNotValidException("Key is not valid.");
		}
		
		boolean isURLDeleted = false;
		
		try {
			isURLDeleted = repository.delete(strKey);
		} catch (DataAccessException e) {
			throw new JdbcException("Database error. Couldn't delete the URL.");
		}
		
		if (!isURLDeleted) {
			throw new KeyNotFoundException("Key is not found.");			
		}		
	}
	
	/**
	 * Returns a List of all TinyURLs.
	 * @return		the list of TinyURLs	
	 * @throws 		JdbcException 
	 */	
	public List<TinyURL> getAll() throws JdbcException {		
		List<TinyURL> tinyURLs = null;
		
		try {
			tinyURLs = repository.getAll();
		} catch (DataAccessException e) {
			throw new JdbcException("Database error. Couldn't get the URLs.");
		}
		
		return tinyURLs;
	}
	
	/**
	 * Finds and returns an URL by key.
	 * @param key	the key of the TinyURL
	 * @return		the URL or returns null if not found	
	 * @throws 		KeyNotValidException 
	 * @throws 		UrlNotFoundException 
	 * @throws 		JdbcException
	 */	
	public String getURL(String strKey) throws KeyNotValidException, UrlNotFoundException, JdbcException {
		if (!keyGeneratorService.isValid(strKey)) {
			throw new KeyNotValidException("Key is not valid.");
		}
		
		TinyURL tinyURL = null;	
		
		try {
			tinyURL = repository.getTinyURL(strKey);
		} catch (DataAccessException e) {
			throw new JdbcException("Database error. Couldn't get the URL.");
		}
		
		if (tinyURL == null) {
			throw new UrlNotFoundException("URL not found.");			
		}
		
		return tinyURL.getUrl();		
	}
	
	/**
	 * Adds the url to the database and returns it's key.
	 * @param url	the url to be added
	 * @return		the key for the new TinyURL	
	 * @throws 		UrlNotValidException 
	 * @throws 		JdbcException
	 */	
	public String addURL(String url) throws UrlNotValidException, JdbcException {		
		// Check if the URL is valid
		if (!urlValidatorService.isValid(url)) {
			throw new UrlNotValidException("Not a valid HTTP URL. Use a full HTTP URL: e.g. http://www.google.com");
		}			
		
		// Use our KeyGenerator to generate a key to the url
		String key = keyGeneratorService.getKey();
		TinyURL tinyURL = new TinyURL(key, url);
		
		// Add to the repository
		try {
			repository.add(tinyURL);
		} catch (DataAccessException e) {
			throw new JdbcException("Database error. Couldn't add the URL.");
		}
		
		// Everything went OK, return the key
		return key;
	}	

}