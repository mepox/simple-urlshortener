package com.laszlojanku.spring.urlshortener.model;

/** 
 * Represent a TinyURL.
 */
public class TinyURL {	
	private String url;	
	private String key;	
	
	public TinyURL(String key, String url) {
		this.key = key;
		this.url = url;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public String getKey() {
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