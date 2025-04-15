package com.vasco.service.imp;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.ProcessMonitorMessage;
import com.vasco.service.ProcessMonitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ProcessMonitorServiceImp implements ProcessMonitorService {

    private final Logger logger = Logger.getLogger(ProcessMonitorServiceImp.class.getName());

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public void send(String topic, ProcessMonitorMessage kafkaMessage) {

            messageProducer.sendMonitorMessage(topic, kafkaMessage);
            logger.info("Message sent to Atlas topic: " + topic + " /n message: " + kafkaMessage );

    }


}
