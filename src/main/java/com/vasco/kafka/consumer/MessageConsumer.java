package com.vasco.kafka.consumer;

import com.vasco.model.KafkaMessageModel;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

    @KafkaListener(topics = "test-print-topic", groupId = "kafka_test_consumer")
    public void listen(KafkaMessageModel message) {
        System.out.println("Received message: " + message.toString());
        System.out.println("TenantID " + message.getTenantId());
        System.out.println("Version " + message.getVersion());
        System.out.println("User " + message.getUserId());
        System.out.println("Action " + message.getActionCode());
    }

}