package com.vasco.kafka.consumer;

import com.vasco.model.DataObject;
import com.vasco.model.HandoverMessage;
import com.vasco.model.ProcessMonitorMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class MessageConsumer {
    private final Logger logger = Logger.getLogger(MessageConsumer.class.getName());

    @KafkaListener(topics = "testTopic", groupId = "kafka_test_consumer")
    public void listen(ProcessMonitorMessage message) {


        logger.info("MESSAGE CONSUMER ProcessMonitorMessage: " + message.toString());
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
        System.out.println("isStartProcess " + message.isStartProcess());

    }

    @KafkaListener(topics = "handoverTopic", groupId = "kafka_test_consumer")
    public void listen(HandoverMessage message) {


        logger.info("MESSAGE CONSUMER HandoverMessage: " + message.toString());
        System.out.println("ProcessId " + message.getProcessId());
        System.out.println("ProcessName " + message.getProcessName());
        System.out.println("versionName " + message.getVersionName());
        System.out.println("caseId " + message.getCaseId());
        System.out.println("workflowEndpoint " + message.getNextWorkflowEngine().getEndpoint());
        System.out.println("workflowAuthType " + message.getNextWorkflowEngine().getAuthentication().getType());
        System.out.println("workflowAuthToken " + message.getNextWorkflowEngine().getAuthentication().getToken());
        System.out.println("variablesInvoiceNum " + message.getVariables().getInvoiceNumber());
        System.out.println("variablesAmount " + message.getVariables().getAmount());
        System.out.println("variablesCurrency " + message.getVariables().getCurrency());
        System.out.println("variablesAppStatus " + message.getVariables().getApprovalStatus());
        for(DataObject dataObj: message.getDataObjects()){
            System.out.println("dataObjectName " +dataObj.getName());
            System.out.println("dataObjectName " +dataObj.getId());
            System.out.println("dataObjectName " +dataObj.getUrl());            System.out.println("dataObjectName " +dataObj.getName()); ;
            System.out.println("dataObjectName " +dataObj.getType());
            System.out.println("dataObjectMetaSize " +dataObj.getMetadata().getSize());
            System.out.println("dataObjectMetaUploadTime " +dataObj.getMetadata().getUploadTime());
            System.out.println("dataObjectMetaUploadedBy " +dataObj.getMetadata().getUploadedBy());
        }


    }

}