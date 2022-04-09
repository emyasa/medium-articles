package com.emyasa.controller;

import com.emyasa.dto.CustomMessageRequest;
import com.emyasa.dto.MessageRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageProducerController {

    private static final String TEST_TOPIC = "test-topic";
    private static final String TEST_TOPIC_CUSTOM = "test-topic-custom";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, Object> customKafkaTemplate;

    public MessageProducerController(@Qualifier("kafkaTemplate") KafkaTemplate<String, String> kafkaTemplate,
                                     @Qualifier("customKafkaTemplate") KafkaTemplate<String, Object> customKafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.customKafkaTemplate = customKafkaTemplate;
    }

    @PostMapping("/send-message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendMessage(@RequestBody MessageRequest messageRequest) {
        kafkaTemplate.send(TEST_TOPIC, messageRequest.message());
    }

    @PostMapping("/send-custom-message")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void sendCustomMessage(@RequestBody CustomMessageRequest messageRequest) {
         customKafkaTemplate.send(TEST_TOPIC_CUSTOM, messageRequest);
    }
}
