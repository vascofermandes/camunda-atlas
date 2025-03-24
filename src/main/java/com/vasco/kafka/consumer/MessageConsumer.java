package com.vasco.kafka.consumer;

import com.vasco.model.KafkaMessageModel;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageConsumer {
    private final Logger logger = Logger.getLogger(MessageConsumer.class.getName());

    @KafkaListener(topics = "testTopic", groupId = "kafka_test_consumer")
    public void listen(KafkaMessageModel message) {
        logger.info("Received message from Atlas: " + message.toString());
        System.out.println("EnvironmentId " + message.getEnvironmentId());
        System.out.println("tenantName " + message.getTenantName());
        System.out.println("processId " + message.getProcessId());
        System.out.println("processName " + message.getProcessName());
        System.out.println("versionName " + message.getVersionName());
        System.out.println("type " + message.getType());
        System.out.println("caseId " + message.getCaseId());
        System.out.println("groupName " + message.getGroupName());
        System.out.println("userName " + message.getUserName());
        System.out.println("taskName " + message.getTaskName());
        System.out.println("isStartProcess " + message.getIsStartProcess());

        //TODO: VERIFY with MessageVALIDATOR and implement a service to start the process

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("Process_1dv384a");

        String item = "Start Process after receive message from Atlas Kafka";

        instance.setVariable("itemName", item);
        instance.businessKey("testBusinessKey");

        instance.execute();


    }

}