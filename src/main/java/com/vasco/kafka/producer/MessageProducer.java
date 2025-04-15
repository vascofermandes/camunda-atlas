package com.vasco.kafka.producer;

import com.vasco.model.HandoverMessage;
import com.vasco.model.ProcessMonitorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class MessageProducer {

    @Autowired
    private KafkaTemplate<String, ProcessMonitorMessage> kafkaTemplateMonitor;

    @Autowired
    private KafkaTemplate<String, HandoverMessage> kafkaTemplateHandover;

    public CompletableFuture<SendResult<String, ProcessMonitorMessage>> sendMonitorMessage(String topic, ProcessMonitorMessage message) {
        return kafkaTemplateMonitor.send(topic, message);
    }

    public CompletableFuture<SendResult<String, HandoverMessage>> sendEndProcessMessage(String topic, HandoverMessage endProcessMessage) {
        return kafkaTemplateHandover.send(topic, endProcessMessage);
    }

}