package com.laszlojanku.spring.urlshortener.service;

import java.net.URL;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.laszlojanku.spring.urlshortener.UrlShortenerApplication;
import com.laszlojanku.spring.urlshortener.model.TinyURL;
import com.laszlojanku.spring.urlshortener.repository.JdbcTinyURLRepository;
import com.laszlojanku.spring.urlshortener.repository.TinyURLRepository;

/** 
 * Provides a Service to manipulate the TinyURLs.
 */

@Service
public class TinyURLService {
		
	private KeyGeneratorService keyGeneratorService;
	private TinyURLRepository repository;	
	
	@Autowired
	public TinyURLService(JdbcTinyURLRepository repository, RandomKeyGeneratorService keyGeneratorService) {
		this.repository = repository;
		this.keyGeneratorService = keyGeneratorService;
		
		// Creates a few default TinyURLs
		try {
			addURL("http://www.google.com");
			addURL("http://www.bing.com");
			addURL("http://www.yahoo.com");
			addURL("http://www.facebook.com");
		} catch (Exception e) {
			UrlShortenerApplication.logger.warn("Database error at TinyURLService constructor.");
		}
	}
	
	/**
	 * Deletes the TinyURL from the database using the key.
	 * @param strKey	the key of the TinyURL in String
	 * @throws			Exception if something went wrong
	 */	
	public void deleteURL(String strKey) throws Exception {	
		if (!keyGeneratorService.isValid(strKey)) {
			throw new Exception("Key is not valid.");
		}
		
		boolean isURLDeleted;
		
		try {
			isURLDeleted = repository.delete(strKey);
		} catch (DataAccessException e) {
			throw new Exception("Database error.");
		}
		
		if (!isURLDeleted) {
			throw new Exception("Key is not found.");			
		}		
	}
	
	/**
	 * Returns a List of all TinyURLs.
	 * @return		the list of TinyURLs
	 * @throws		Exception on database error
	 */	
	public List<TinyURL> getAll() throws Exception {
		List<TinyURL> tinyURLs = null;
		
		try {
			tinyURLs = repository.getAll();
		} catch (DataAccessException e) {
			throw new Exception("Database error. Couldn't get the URLs from the server.");
		}
		
		return tinyURLs;
	}
	
	/**
	 * Finds and returns an URL by key.
	 * @param	key	the key of the TinyURL
	 * @return	url	the URL or returns null if not found
	 * @throws		Exception if something went wrong
	 */	
	public String getURL(String strKey) throws Exception {
		if (!keyGeneratorService.isValid(strKey)) {
			throw new Exception("Key is not valid.");
		}
		
		TinyURL tinyURL;
		
		try {
			tinyURL = repository.getTinyURL(strKey);
		} catch (DataAccessException e) {
			throw new Exception("Database error. Couldn't get the url.");
		}
		
		if (tinyURL == null) {
			throw new Exception("URL not found.");
		}
		
		return tinyURL.getUrl();
	}
	
	/**
	 * Adds the url to the database and returns it's key.
	 * @param	url	the url to be added
	 * @return		the key for the new TinyURL
	 * @throws		Exception if something went wrong
	 */	
	public String addURL(String url) throws Exception {		
		// Check if the URL is valid	
		try {
			URL validURL = new URL(url);
		} catch (IllegalArgumentException e) {
			throw new Exception("Not a valid HTTP URL. Use a full HTTP URL: e.g. http://www.google.com");			
		}	
		
		// Use our KeyGenerator to generate a key to the url
		String key = keyGeneratorService.getKey();
		TinyURL tinyURL = new TinyURL(key, url);
		
		// Add to the repository
		try {
			repository.add(tinyURL);
		} catch (DataAccessException e) {
			throw new Exception("Database error.");
		}
		
		// Everything went OK, return the key
		return key;
	}	

}