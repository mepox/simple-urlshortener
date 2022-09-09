package com.laszlojanku.spring.urlshortener.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.laszlojanku.spring.urlshortener.model.TinyURL;

/** 
 * Repository using JdbcTemplate to access the database.
 */

@Repository
public class JdbcTinyURLRepository implements TinyURLRepository {
	
	@Autowired
	private JdbcTemplate jdbc;
	
	/**
	 * Adds a TinyURL to the database using JdbcTemplate.
	 * @param tinyURL	the TinyURL to be added
	 * @throws 			DataAccessException if there is any problem using JdbcTemplate 				
	 */
	@Override
	public void add(TinyURL tinyURL) throws DataAccessException {
		String sql = "INSERT INTO tinyurl (tinyurl_key, url) VALUES (?, ?)";
	
		jdbc.update(sql, tinyURL.getKey(), tinyURL.getUrl());
	}
	
	/**
	 * Deletes a TinyURL from the database using JdbcTemplate
	 * @param key	the key of the TinyURL
	 * @return 		true if successful or false if not found
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public boolean delete(String key) throws DataAccessException {
		// Return false if not found
		if (!isExists(key)) {
			return false;
		}
		
		String sql = "DELETE FROM tinyurl WHERE tinyurl_key = ?";
		
		jdbc.update(sql, key);	
		
		return true;
	}
	
	/**
	 * Returns a List of all the TinyURLs.
	 * @return		null if empty
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public List<TinyURL> getAll() throws DataAccessException {
		List<TinyURL> tinyURLs = new ArrayList<TinyURL>();
		List<Map<String, Object>> tinyURLsMap = new ArrayList<Map<String, Object>>();
		
		String sql = "SELECT * FROM tinyurl";		
		
		tinyURLsMap = jdbc.queryForList(sql);
		
		if (tinyURLsMap != null) {		
			for (Map<String, Object> tinyURLMap : tinyURLsMap) {
				TinyURL tinyURL = buildByRow(tinyURLMap);
				tinyURLs.add(tinyURL);
			}
		}
		
		return tinyURLs;
	}
	
	private TinyURL buildByRow(Map<String, Object> row) {
		// Null check
		if (row == null) {
			return null;
		}
		
		String key = (String) row.get("tinyurl_key");
		String url = (String) row.get("url");
		
		TinyURL tinyURL = new TinyURL(key, url);
		
		return tinyURL;
	}
	
	/**
	 * Finds and returns a TinyURL using a key.
	 * @param key	the key of the TinyURL
	 * @return 		a TinyURL or returns null if not found
	 * @throws 		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public TinyURL getTinyURL(String key) throws DataAccessException {
		// Return null if key not exists
		if (!isExists(key)) {
			return null;
		}		
		
		String sql = "SELECT * FROM tinyurl WHERE tinyurl_key = ?";
		
		Map<String, Object> row = jdbc.queryForMap(sql, key);
		
		return buildByRow(row);
	}
	
	/**
	 * Checks if the key is found in the database.
	 * @param key	the key of the TinyURL
	 * @return		true if found
	 * @throws		DataAccessException if there is any problem using JdbcTemplate
	 */
	@Override
	public boolean isExists(String key) throws DataAccessException {
		String sql = "SELECT count(*) FROM tinyurl WHERE tinyurl_key = ?";		
		
		int count = jdbc.queryForObject(sql, Integer.class, key);
		
		return (count > 0) ? true : false;
	}

}