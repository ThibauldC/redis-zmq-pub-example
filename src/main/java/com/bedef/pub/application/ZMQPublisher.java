package com.bedef.redispub.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

import java.util.List;

@Component
@ConditionalOnProperty(name="queue", havingValue = "zmq")
public class ZMQPublisher implements MessagePublisher {

    @Value("${zmq.min-subscribers:1}")
    int minSubscribers;

    @Override
    public void publish(List<String> messages, String channel) {
        try(ZContext context = new ZContext()){
            Socket publisher = context.createSocket(SocketType.PUB);

            publisher.setLinger(5000);
            publisher.setSndHWM(0);
            publisher.bind("tcp://*:5561");

            Socket syncService = context.createSocket(SocketType.REP);
            syncService.bind("tcp://*:5562");

            System.out.printf("Waiting for %d subscribers...%n", minSubscribers);

            int subscribers = 0;

            //Waiting for subscribers to be present or else the messages are lost
            while(subscribers < minSubscribers){
                syncService.recv(0);

                syncService.send("", 0);
                subscribers++;
            }
            for(String message: messages){
                publisher.send(channel + " " + message, 0);
            }

            publisher.send(channel + " " + "END", 0);
        }
    }
}
