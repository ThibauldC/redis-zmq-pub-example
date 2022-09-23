package com.bedef.redispub.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisMessagePublisher {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public void publish(final String message, final ChannelTopic topic) {
        redisTemplate.convertAndSend(topic.getTopic(), message);
    }
}
