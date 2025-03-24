package com.vasco.service;

import com.vasco.model.KafkaMessageModel;

public interface AtlasCommunicationService {

    void send(KafkaMessageModel kafkaMessage);

}
