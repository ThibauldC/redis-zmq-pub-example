package com.bedef.redispub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class RedisMessagePublisher {

    @Autowired
    StringRedisTemplate redisTemplate;

    public void publish(final String message, final String streamName) {
        final StringRecord record = StreamRecords.string(Collections.singletonMap("info", message)).withStreamKey(streamName);
        redisTemplate.opsForStream().add(record);
    }
}
