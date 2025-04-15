package com.vasco.service;

import com.vasco.model.ProcessMonitorMessage;

public interface ProcessMonitorService {

    void send(String topic, ProcessMonitorMessage kafkaMessage);

}
