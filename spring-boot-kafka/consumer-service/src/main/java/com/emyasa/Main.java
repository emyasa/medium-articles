package com.emyasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void receiveTestMessage(String message) {
        System.out.println(message);
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group")
    public void receiveTestMessage2(String message) {
        System.out.println(message);
    }

    @KafkaListener(topics = "test-topic", groupId = "test-group2")
    public void receiveTestMessage3(String message) {
        System.out.println(message);
    }

}
