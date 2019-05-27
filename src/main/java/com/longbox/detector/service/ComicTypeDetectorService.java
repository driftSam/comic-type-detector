package com.longbox.detector.service;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ComicTypeDetectorService {

	private static Logger LOG = LoggerFactory.getLogger(ComicTypeDetectorService.class);

	@Value("${working.dir}")
	String dirName;

	Tika tika = new Tika();

	public void detect() {
		LOG.info(dirName);
		Path workingDir = Paths.get(dirName);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(workingDir)) {
			for (Path entry : stream) {
				System.out.println(Files.probeContentType(entry));

				System.out.println("From Tika: " + tika.detect(entry));
			}

		} catch (IOException e) {
			LOG.error("Error");
			e.printStackTrace();
		}
	}

}
