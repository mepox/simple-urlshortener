package com.laszlojanku.spring.urlshortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.laszlojanku.spring.urlshortener.UrlShortenerApplication;

/**
 * Run methods after the application started. 
 */
@Service
public class StartupService {
	
	@Autowired
	private TinyURLService tinyURLService;
	
	@EventListener(ApplicationReadyEvent.class)
	private void addSomeUrlAfterStartup() {
		// Creates a few default TinyURLs
		try {
			tinyURLService.addURL("http://www.google.com");
			tinyURLService.addURL("http://www.bing.com");
			tinyURLService.addURL("http://www.yahoo.com");
			tinyURLService.addURL("http://www.facebook.com");
		} catch (Exception e) {
			UrlShortenerApplication.logger.warn(e.getMessage());
		}
	}

}