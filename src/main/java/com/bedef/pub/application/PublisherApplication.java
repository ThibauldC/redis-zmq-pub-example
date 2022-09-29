package com.bedef.pub.application;

import com.bedef.pub.domain.PersonInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class PublisherApplication implements CommandLineRunner {

	@Value("${data.path}")
	private String path;

	@Value("${publisher.channel}")
	private String channel;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	MessagePublisher messagePublisher;

	public static void main(String[] args) {
		SpringApplication.run(PublisherApplication.class, args);
	}

	@SneakyThrows
	public void run(String... args){
		PersonInfo[] infos = mapper.readValue(Paths.get(path).toFile(), PersonInfo[].class);

		List<String> messages = Arrays.stream(infos)
				.map(this::infoToString)
				.toList()
				.subList(0, 3);

		messagePublisher.publish(messages, channel);

		System.out.println("All messages sent successfully!");
	}

	@SneakyThrows
	private String infoToString(PersonInfo info){
		return this.mapper.writeValueAsString(info);
	}
}
