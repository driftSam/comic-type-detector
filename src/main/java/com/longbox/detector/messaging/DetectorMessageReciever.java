package com.longbox.detector.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.longbox.detector.service.ComicTypeDetectorService;

@Component
public class DetectorMessageReciever {
	@Autowired
	ComicTypeDetectorService service;

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
		service.detect(message);

	}
}
