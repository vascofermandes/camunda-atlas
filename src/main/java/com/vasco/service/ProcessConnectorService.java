package com.vasco.service;

import com.vasco.model.ProcessMonitorMessage;

public interface ProcessConnectorService {

    void send(ProcessMonitorMessage kafkaMessage);

}
