package com.emyasa;

import com.emyasa.dto.CustomMessageRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;

@SpringBootApplication
public class Main {

    private static final String TEST_TOPIC = "test-topic";
    private static final String TEST_TOPIC_CUSTOM = "test-topic-custom";

    private static final String TEST_GROUP = "test-group";
    private static final String TEST_GROUP_NEW = "test-group-new";

    private static final String SIMPLE_CONTAINER_FACTORY = "simpleKafkaListenerContainerFactory";
    private static final String CUSTOM_CONTAINER_FACTORY = "customKafkaListenerContainerFactory";

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @KafkaListener(topics = TEST_TOPIC, groupId = TEST_GROUP, containerFactory = SIMPLE_CONTAINER_FACTORY)
    public void listener1(String message) {
        System.out.println("Message: '" + message + "' from listener1");
    }

    @KafkaListener(topics = TEST_TOPIC, groupId = TEST_GROUP, containerFactory = SIMPLE_CONTAINER_FACTORY)
    public void listener2(String message) {
        System.out.println("Message: '" + message + "' from listener2");
    }

    @KafkaListener(topics = TEST_TOPIC, groupId = TEST_GROUP_NEW, containerFactory = SIMPLE_CONTAINER_FACTORY)
    public void listener3(String message) {
        System.out.println("Message: '" + message + "' from listener3");
    }

    @KafkaListener(topics = TEST_TOPIC_CUSTOM, groupId = TEST_GROUP, containerFactory = CUSTOM_CONTAINER_FACTORY)
    public void customListener(CustomMessageRequest message, Acknowledgment acknowledgment) {
        System.out.println("Message: '" + message.payload() + "' from customListener");
        acknowledgment.acknowledge();
    }
}
