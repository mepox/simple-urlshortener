package com.laszlojanku.spring.urlshortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Run methods after the application started. 
 */
@Service
public class StartupService {
	
	private final Logger logger = LoggerFactory.getLogger(StartupService.class);
	
	@Autowired
	private TinyURLService tinyURLService;
	
	@EventListener(ApplicationReadyEvent.class)
	private void addSomeUrlAfterStartup() {
		// Creates a few default TinyURLs
		logger.info("Adding some default URLs...");
		try {
			tinyURLService.addURL("http://www.google.com");
			tinyURLService.addURL("http://www.bing.com");
			tinyURLService.addURL("http://www.yahoo.com");
			tinyURLService.addURL("http://www.facebook.com");
		} catch (Exception e) {
			logger.warn("Couldn't add the default URLs at startup: " + e.getMessage());
		}
	}

}