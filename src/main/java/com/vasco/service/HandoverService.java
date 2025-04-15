package com.vasco.service;

import com.vasco.model.HandoverMessage;

public interface HandoverService {

    void startProcess(String processKey, HandoverMessage handoverMessage);

    void notifyEndProcess(String topic, String processKey, HandoverMessage handoverMessage);

}