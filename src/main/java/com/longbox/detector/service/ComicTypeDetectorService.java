package com.longbox.detector.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComicTypeDetectorService {

	private static Logger LOG = LoggerFactory.getLogger(ComicTypeDetectorService.class);

	final String cbr = "application/x-cbr";
	final String cbz = "application/x-cbz";
	final String directExchangeName = "comic-exchange";

	@Autowired
	RabbitTemplate rabbitTemplate;

	public void detect(String comicName) {
		LOG.info(comicName);
		Path comicPath = Paths.get(comicName);

		try {
			String contentType = Files.probeContentType(comicPath);
			switch (contentType) {
			case cbr:
				System.out.println(cbr);
				rabbitTemplate.convertAndSend(directExchangeName, "rar", comicPath.toString());
				break;
			case cbz:
				System.out.println(cbz);
				rabbitTemplate.convertAndSend(directExchangeName, "zip", comicPath.toString());
				break;
			default:
				System.out.println(contentType);
				rabbitTemplate.convertAndSend(directExchangeName, "other", comicPath.toString());
				break;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
