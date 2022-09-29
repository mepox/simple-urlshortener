package com.laszlojanku.spring.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.laszlojanku.spring.urlshortener.model.TinyURL;

/** 
 * Repository using an ArrayList.
 * 
 * Stores the TinyURLs in an ArrayList. Used for prototyping.
 */
@Repository
public class ArrayListTinyURLRepository implements TinyURLRepository {
	
	private List<TinyURL> tinyURLs = new ArrayList<>();

	/**
	 * Adds a TinyURL to the ArrayList.
	 * 
	 * @param tinyURL	the TinyURL to be added	 				
	 */
	@Override
	public void add(TinyURL tinyURL) {
		tinyURLs.add(tinyURL);		
	}
	
	/**
	 * Deletes a TinyURL from the ArrayList.
	 * 
	 * @param key	the key of the TinyURL
	 * @return 		true if successful
	 */
	@Override
	public boolean delete(String key) {
		// First find the index
		int indexToRemove = -1;
		for (int i = 0; i < tinyURLs.size(); i++) {
			if (tinyURLs.get(i).getKey() == key) {
				indexToRemove = i;
				break;
			}
		}
		
		// Remove the key by index
		if (indexToRemove > -1) {
			tinyURLs.remove(indexToRemove);
			return true;
		}
		
		// Not found, return false
		return false;
	}

	/**
	 * Returns a List of all TinyURLs.
	 */
	@Override
	public List<TinyURL> getAll() {
		return tinyURLs;		
	}
	
	/**
	 * Finds and returns the TinyURL by key.
	 * 
	 * @param key	the key of the TinyURL
	 * @return 		the TinyURL or null if not found
	 */
	@Override
	public TinyURL getTinyURL(String key) {		
		for (TinyURL tinyURL : tinyURLs) {
			if (tinyURL.getKey() == key) {
				return tinyURL;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the key is exists in the ArrayList.
	 * 
	 * @param key	the key of the TinyURL
	 * @return		true if found
	 */	
	@Override
	public boolean isExists(String key) {		
		for (TinyURL tinyURL : tinyURLs) {
			if (tinyURL.getKey() == key) {
				// Found so return true
				return true;
			}
		}
		
		// Not found
		return false;
	}	

}