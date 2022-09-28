package com.bedef.redispub.application;

import com.bedef.redispub.domain.PersonInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.Arrays;

@SpringBootApplication
public class RedisPubApplication implements CommandLineRunner {

	@Value("${data.path}")
	private String path;

	@Value("${redis.stream}")
	private String streamName;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	RedisMessagePublisher redisMessagePublisher;

	public static void main(String[] args) {
		SpringApplication.run(RedisPubApplication.class, args);
	}

	@SneakyThrows
	public void run(String... args){
		PersonInfo[] infos = mapper.readValue(Paths.get(path).toFile(), PersonInfo[].class);

		for(PersonInfo info: Arrays.stream(infos).toList().subList(0, 3)){
			redisMessagePublisher.publish(mapper.writeValueAsString(info), streamName);
		}

		System.out.println("All messages sent successfully!");
	}

}
