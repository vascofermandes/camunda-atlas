package com.vasco.service.imp;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.ProcessMonitorMessage;
import com.vasco.service.ProcessConnectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ProcessConnectorServiceImp implements ProcessConnectorService {

    private final Logger logger = Logger.getLogger(ProcessConnectorServiceImp.class.getName());

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public void send(ProcessMonitorMessage kafkaMessage) {

            messageProducer.sendMessage("testTopic", kafkaMessage);

            logger.info("Message sent to Atlas ProcessMonitor" + kafkaMessage );

    }


}
