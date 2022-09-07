package com.laszlojanku.spring.urlshortener.repository;

import java.util.List;

import com.laszlojanku.spring.urlshortener.model.TinyURL;

/**
 * Interface for the TinyURLRepositories
 */
public interface TinyURLRepository {
	
	public void add(TinyURL tinyURL);
	public boolean delete(int key);	
	public List<TinyURL> getAll();
	public TinyURL getTinyURL(int key);
	public boolean isExists(int key);
	
}