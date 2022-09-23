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

	@Value("${redis.first-topic}")
	private String firstTopic;

	@Value("${redis.second-topic}")
	private String secondTopic;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	RedisMessagePublisher redisMessagePublisher;

	public static void main(String[] args) {
		SpringApplication.run(RedisPubApplication.class, args);
	}

	@SneakyThrows
	public void run(String... args){
		List<PersonInfo> infos = Arrays.asList(
				mapper.readValue(Paths.get(path).toFile(), PersonInfo[].class)
		);

		List<PersonInfo> infosFirstTopic = infos.subList(0, infos.size()/2);
		List<PersonInfo> infosSecondTopic = infos.subList(infos.size()/2, infos.size());

		ChannelTopic topic1 = ChannelTopic.of(firstTopic);
		ChannelTopic topic2 = ChannelTopic.of(secondTopic);


		for(PersonInfo info: infosFirstTopic){
			redisMessagePublisher.publish(mapper.writeValueAsString(info), topic1);
		}

		for(PersonInfo info: infosSecondTopic){
			redisMessagePublisher.publish(mapper.writeValueAsString(info), topic2);
		}

		System.out.println("All messages sent successfully!");
	}

}
