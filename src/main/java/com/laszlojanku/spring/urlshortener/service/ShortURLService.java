package com.laszlojanku.spring.urlshortener.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.laszlojanku.spring.urlshortener.UrlShortenerApplication;
import com.laszlojanku.spring.urlshortener.model.ShortURL;
import com.laszlojanku.spring.urlshortener.repository.JdbcShortURLRepository;
import com.laszlojanku.spring.urlshortener.repository.ShortURLRepository;

/** 
 * Provides a Service to manipulate the ShortURLs in the database.
 */

@Service
public class ShortURLService {
		
	private KeyGeneratorService keyGeneratorService;
	private ShortURLRepository repository;	
	
	@Autowired
	public ShortURLService(JdbcShortURLRepository repository, SimpleKeyGeneratorService keyGeneratorService) {
		this.repository = repository;
		this.keyGeneratorService = keyGeneratorService;
		
		// Creates a few default ShortURLs
		try {
			addURL("http://www.google.com");
			addURL("http://www.bing.com");
			addURL("http://www.yahoo.com");
			addURL("http://www.facebook.com");
		} catch (Exception e) {
			UrlShortenerApplication.logger.warn("Database error at ShortURLService constructor.");
		}
	}
	
	/**
	 * Deletes the ShortURL from the database using the key.
	 * @param strKey	the key of the ShortURL in String
	 * @throws			Exception if something went wrong
	 */	
	public void deleteURL(String strKey) throws Exception {
		// First validate the key as we need an Integer
		int key = 0;
		
		try {
			key = Integer.parseInt(strKey);
		} catch (NumberFormatException e) {
			throw new Exception("Key is not valid.");
		}
		
		boolean isURLDeleted;
		
		try {
			isURLDeleted = repository.delete(key);
		} catch (DataAccessException e) {
			throw new Exception("Database error.");
		}
		
		if (!isURLDeleted) {
			throw new Exception("Key is not found.");			
		}		
	}
	
	/**
	 * Returns a List of all ShortURLs.
	 * @return		the list of ShortURLs
	 * @throws		Exception on database error
	 */	
	public List<ShortURL> getAll() throws Exception {
		List<ShortURL> shortURLs = null;
		
		try {
			shortURLs = repository.getAll();
		} catch (DataAccessException e) {
			throw new Exception("Database error. Couldn't get the URLs from the server.");
		}
		
		return shortURLs;
	}
	
	/**
	 * Finds and returns an URL by key.
	 * @param	key	the key of the ShortURL
	 * @return	url	the URL or returns null if not found
	 * @throws		Exception if something went wrong
	 */	
	public String getURL(String strKey) throws Exception {
		// First validate the key as we need an Integer
		int key = 0;
		
		try {
			key = Integer.parseInt(strKey);
		} catch (NumberFormatException e) {
			throw new Exception("Key is not valid.");
		}
		
		ShortURL shortURL;
		
		try {
			shortURL = repository.getShortURL(key);
		} catch (DataAccessException e) {
			throw new Exception("Database error. Couldn't get the url.");
		}
		
		if (shortURL == null) {
			throw new Exception("URL not found.");
		}
		
		return shortURL.getUrl();
	}
	
	/**
	 * Adds the url to the database and returns it's key.
	 * @param	url	the url to be added
	 * @return		the key for the new ShortURL
	 * @throws		Exception if something went wrong
	 */	
	public int addURL(String url) throws Exception {		
		// Check if the URL is valid	
		try {
			URI uri = URI.create(url);
		} catch (IllegalArgumentException e) {
			throw new Exception("Not a valid HTTP URL. Use a full HTTP URL: e.g. http://www.google.com");			
		}	
		
		// Use our KeyGenerator to generate a key to the url
		int key = keyGeneratorService.getKey();
		ShortURL shortURL = new ShortURL(key, url);
		
		// Add to the repository
		try {
			repository.add(shortURL);
		} catch (DataAccessException e) {
			throw new Exception("Database error.");
		}
		
		// Everything went OK, return the key
		return key;
	}	

}