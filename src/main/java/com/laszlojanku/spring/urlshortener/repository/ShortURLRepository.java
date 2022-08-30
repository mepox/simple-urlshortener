package com.laszlojanku.spring.urlshortener.repository;

import java.util.List;

import com.laszlojanku.spring.urlshortener.model.ShortURL;

/**
 * Interface for the ShortURLRepositories
 */
public interface ShortURLRepository {
	
	public void add(ShortURL shortURL);
	public boolean delete(int key);	
	public List<ShortURL> getAll();
	public ShortURL getShortURL(int key);
	public boolean isExists(int key);
	
}