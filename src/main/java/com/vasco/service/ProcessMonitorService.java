package com.vasco.service;

import com.vasco.model.ProcessMonitorMessage;

public interface ProcessMonitorService {

    void send(ProcessMonitorMessage kafkaMessage);

}
