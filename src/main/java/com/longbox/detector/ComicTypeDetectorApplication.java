package com.longbox.detector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.longbox.detector.service.ComicTypeDetectorService;

@SpringBootApplication
public class ComicTypeDetectorApplication implements CommandLineRunner {

	@Autowired
	ComicTypeDetectorService service;

	private static Logger LOG = LoggerFactory.getLogger(ComicTypeDetectorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ComicTypeDetectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.detect();
	}

}
