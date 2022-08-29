package com.laszlojanku.spring.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.laszlojanku.spring.urlshortener.model.ShortURL;

/** 
 * Repository using JdbcTemplate to access the database.
 */

@Repository
public class JdbcShortURLRepository implements ShortURLRepository {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * Adds a ShortURL to the database using JdbcTemplate.
	 * @param shortURL	the ShortURL to be added
	 * @throws 			DataAccessException if there is any problem using JdbcTemplate 				
	 */
	@Override
	public void add(ShortURL shortURL) throws DataAccessException {
		String sql = "INSERT INTO shorturl (shorturl_key, url) VALUES (?, ?)";
		//Object[] params = { shortURL.getKey(), shortURL.getUrl() };		
	
		jdbc.update(sql, shortURL.getKey(), shortURL.getUrl());
	}
	
	/**
	 * Deletes a ShortURL from the database using JdbcTemplate
	 * @param key	the key of the ShortURL
	 * @return 		true if successful or false if not found
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public boolean delete(int key) throws DataAccessException {
		// Return false if not found
		if (!isExists(key)) {
			return false;
		}
		
		String sql = "DELETE FROM shorturl WHERE shorturl_key = ?";
		
		jdbc.update(sql, key);	
		
		return true;
	}
	
	/**
	 * Returns a List of all the ShortURLs.
	 * @return		null if empty
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public List<ShortURL> getAll() throws DataAccessException {
		List<ShortURL> shortURLs = new ArrayList<ShortURL>();
		List<Map<String, Object>> shortURLsMap = new ArrayList<Map<String, Object>>();
		
		String sql = "SELECT * FROM shorturl";		
		
		shortURLsMap = jdbc.queryForList(sql);
		
		if (shortURLsMap != null) {		
			for (Map<String, Object> shortURLMap : shortURLsMap) {
				ShortURL shortURL = buildByRow(shortURLMap);
				shortURLs.add(shortURL);
			}
		}
		
		return shortURLs;
	}
	
	private ShortURL buildByRow(Map<String, Object> row) {
		// Null check
		if (row == null) {
			return null;
		}
		
		int key = (int) row.get("shorturl_key");
		String url = (String) row.get("url");
		
		ShortURL shortURL = new ShortURL(key, url);
		
		return shortURL;
	}
	
	/**
	 * Finds and returns a ShortURL using a key.
	 * @param key	the key of the ShortURL
	 * @return 		a ShortURL or returns null if not found
	 * @throws 		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public ShortURL getShortURL(int key) throws DataAccessException {
		// Return null if key not exists
		if (!isExists(key)) {
			return null;
		}		
		
		String sql = "SELECT * FROM shorturl WHERE shorturl_key = ?";
		
		Map<String, Object> row = jdbc.queryForMap(sql, key);
		
		return buildByRow(row);
	}
	
	/**
	 * Checks if the key is found in the database.
	 * @param key	the key of the ShortURL
	 * @return		true if found
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public boolean isExists(int key) throws DataAccessException {
		String sql = "SELECT count(*) FROM shorturl WHERE shorturl_key = ?";		
		
		int count = jdbc.queryForObject(sql, Integer.class, key);
		
		return (count > 0) ? true : false;
	}

}