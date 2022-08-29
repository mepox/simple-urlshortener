package com.laszlojanku.spring.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.laszlojanku.spring.urlshortener.model.ShortURL;

/** 
 * Repository using an ArrayList.
 * 
 * Stores the ShortURLs in an ArrayList. Used for prototyping.
 */

@Repository
public class ArrayListShortURLRepository implements ShortURLRepository {
	
	private List<ShortURL> shortURLs = new ArrayList<>();

	/**
	 * Adds a ShortURL to the ArrayList.
	 * @param shortURL	the ShortURL to be added	 				
	 */
	@Override
	public void add(ShortURL shortURL) {
		shortURLs.add(shortURL);		
	}
	
	/**
	 * Deletes a ShortURL from the ArrayList.
	 * @param key	the key of the ShortURL
	 * @return 		true if successful
	 */
	@Override
	public boolean delete(int key) {
		// First find the index
		int indexToRemove = -1;
		for (int i = 0; i < shortURLs.size(); i++) {
			if (shortURLs.get(i).getKey() == key) {
				indexToRemove = i;
				break;
			}
		}
		
		// Remove the key by index
		if (indexToRemove > -1) {
			shortURLs.remove(indexToRemove);
			return true;
		}
		
		// Not found, return false
		return false;
	}

	/**
	 * Returns a List of all ShortURLs.
	 */
	@Override
	public List<ShortURL> getAll() {
		return shortURLs;		
	}
	
	/**
	 * Finds and returns the ShortURL by key.
	 * @param key	the key of the ShortURL
	 * @return 		the ShortURL or null if not found
	 */
	@Override
	public ShortURL getShortURL(int key) {
		for (ShortURL shortURL : shortURLs) {
			if (shortURL.getKey() == key) {
				return shortURL;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the key is exists in the ArrayList.
	 * @param key	the key of the ShortURL
	 * @return		true if found
	 */	
	@Override
	public boolean isExists(int key) {		
		for (ShortURL shortURL : shortURLs) {
			if (shortURL.getKey() == key) {
				// Found so return true
				return true;
			}
		}
		
		// Not found
		return false;
	}	

}