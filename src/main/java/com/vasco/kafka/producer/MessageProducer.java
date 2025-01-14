package com.vasco.kafka.producer;

import com.vasco.model.KafkaMessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, KafkaMessageModel> kafkaTemplate;

    public void sendMessage(String topic, KafkaMessageModel message) {
        kafkaTemplate.send(topic, message);
    }

}