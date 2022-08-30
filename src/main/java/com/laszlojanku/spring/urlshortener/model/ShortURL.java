package com.laszlojanku.spring.urlshortener.model;

/** 
 * Represent a ShortURL.
 */

public class ShortURL {	
	private String url;	
	private int key;	
	
	public ShortURL(int key, String url) {
		this.key = key;
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public int getKey() {
		return this.key;
	}
	
	/**
	 * Returns the class's fields in a JSON String
	 * @return	JSON String
	 */
	@Override
	public String toString() {
		return "{ \"url\" : \"" + url + "\", \"key\" : \"" + key + "\" }";
	}
	
}