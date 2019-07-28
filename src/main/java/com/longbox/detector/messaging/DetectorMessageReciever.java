package com.longbox.detector.messaging;

import org.springframework.stereotype.Component;

@Component
public class DetectorMessageReciever {

	public void receiveMessage(String message) {
		System.out.println("Received <" + message + ">");
	}
}
