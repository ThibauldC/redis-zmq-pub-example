package com.bedef.redispub.application;

import com.bedef.redispub.domain.PersonInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.listener.ChannelTopic;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class RedisPubApplication implements CommandLineRunner {

	@Value("${data.path}")
	private String path;

	@Value("${redis.topic}")
	private String topic;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	RedisMessagePublisher redisMessagePublisher;

	public static void main(String[] args) {
		SpringApplication.run(RedisPubApplication.class, args);
	}

	@SneakyThrows
	public void run(String... args){
		List<PersonInfo> personInfos = Arrays.asList(
				mapper.readValue(Paths.get(path).toFile(), PersonInfo[].class)
		);

		redisMessagePublisher.publish(personInfos.get(1).toString(), ChannelTopic.of(topic));
	}

}
