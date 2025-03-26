package com.vasco.kafka.producer;

import com.vasco.model.ProcessMonitorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, ProcessMonitorMessage> kafkaTemplate;

    public void sendMessage(String topic, ProcessMonitorMessage message) {
        kafkaTemplate.send(topic, message);
    }

}