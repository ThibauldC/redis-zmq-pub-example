package com.bedef.pub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.connection.stream.StringRecord;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@ConditionalOnProperty(name="queue", havingValue = "redis", matchIfMissing = true)
public class RedisMessagePublisher implements MessagePublisher {

    @Autowired
    StringRedisTemplate redisTemplate;

    public void publish(final List<String> messages, final String channel) {
        for(String message: messages){
            final StringRecord record = StreamRecords.string(Collections.singletonMap("info", message)).withStreamKey(channel);
            redisTemplate.opsForStream().add(record);
        }
    }
}
