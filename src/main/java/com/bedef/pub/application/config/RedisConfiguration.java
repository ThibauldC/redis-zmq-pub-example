package com.bedef.pub.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
@ConditionalOnProperty(name="queue", havingValue = "redis", matchIfMissing = true)
public class RedisConfiguration {

    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;


    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);

        return new JedisConnectionFactory(config);
    }

    @Bean
    StringRedisTemplate redisTemplate(){
        final StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
}
