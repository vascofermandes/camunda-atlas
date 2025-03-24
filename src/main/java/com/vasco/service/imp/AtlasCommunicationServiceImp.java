package com.vasco.service.imp;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.KafkaMessageModel;
import com.vasco.service.AtlasCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AtlasCommunicationServiceImp implements AtlasCommunicationService {

    private final Logger logger = Logger.getLogger(AtlasCommunicationServiceImp.class.getName());

    @Autowired
    private MessageProducer messageProducer;

    @Override
    public void send(KafkaMessageModel kafkaMessage) {

            messageProducer.sendMessage("testTopic", kafkaMessage);

            logger.info("Message sent to Atlas Kafka" + kafkaMessage );

    }


}
