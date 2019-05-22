package com.longbox.detector;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ComicTypeDetectorApplication implements CommandLineRunner {

	@Value("${working.dir}")
	String dirName;

	private static Logger LOG = LoggerFactory.getLogger(ComicTypeDetectorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ComicTypeDetectorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Tika tika = new Tika();
		LOG.info(dirName);
		Path workingDir = Paths.get(dirName);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(workingDir)) {
			for (Path entry : stream) {
				System.out.println(Files.probeContentType(entry));

				System.out.println("From Tika: " + tika.detect(entry));
			}

		}
	}

}
