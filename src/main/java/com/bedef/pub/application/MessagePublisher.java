package com.bedef.pub.application;

import java.util.List;

public interface MessagePublisher {

    void publish(final List<String> messages, final String channel);
}
