package com.bedef.redispub;

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
public class RedisPubApplication implements CommandLineRunner {

	@Value("${data.path}")
	private String path;

	@Autowired
	private ObjectMapper mapper;

	public static void main(String[] args) {
		SpringApplication.run(RedisPubApplication.class, args);
	}

	@SneakyThrows
	public void run(String... args){
		List<PersonInfo> personInfos = Arrays.asList(
				mapper.readValue(Paths.get(path).toFile(), PersonInfo[].class)
		);

		personInfos.forEach(System.out::println);
	}

}
