package com.vasco.service.imp;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.CamundaDataObject;
import com.vasco.model.DataObject;
import com.vasco.model.HandoverMessage;
import com.vasco.service.HandoverService;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class HandoverServiceImp implements HandoverService {

    private final Logger logger = Logger.getLogger(HandoverServiceImp.class.getName());

    @Autowired
    private MessageProducer messageProducer;


    @Override
    public void startProcess(String processKey, HandoverMessage handoverMessage) {

        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey(processKey);

        String item = "Start Process " + processKey + " after receive message from Atlas Kafka";

        instance.setVariable("processId", handoverMessage.getProcessId());
        instance.setVariable("processName", handoverMessage.getProcessName());
        instance.setVariable("versionName", handoverMessage.getVersionName());
        instance.setVariable("caseId", handoverMessage.getCaseId());
        instance.setVariable("nextEndpoint", handoverMessage.getNextWorkflowEngine().getEndpoint());
        instance.setVariable("nextAuthType", handoverMessage.getNextWorkflowEngine().getAuthentication().getType());
        instance.setVariable("nextAuthToken", handoverMessage.getNextWorkflowEngine().getAuthentication().getToken());
        instance.setVariable("nextVarInvNumber", handoverMessage.getVariables().getInvoiceNumber());
        instance.setVariable("nextVarAmourt", handoverMessage.getVariables().getAmount());
        instance.setVariable("nextVarCurrency", handoverMessage.getVariables().getCurrency());
        instance.setVariable("nextVarCustId", handoverMessage.getVariables().getCustomerId());
        instance.setVariable("nextVarAppStatus", handoverMessage.getVariables().getApprovalStatus());

        List<CamundaDataObject> dataObjectsList = new ArrayList<CamundaDataObject>();

        for(DataObject dataObject : handoverMessage.getDataObjects()){
            CamundaDataObject cdo = new CamundaDataObject();
            cdo.setId(dataObject.getId());
            cdo.setName(dataObject.getName());
            cdo.setType(dataObject.getType());
            cdo.setUrl(dataObject.getUrl());
            cdo.setMetadataSize(dataObject.getMetadata().getSize());
            cdo.setMetadataUploadTime(dataObject.getMetadata().getUploadTime());
            cdo.setMetadataUploadedBy(dataObject.getMetadata().getUploadedBy());

            dataObjectsList.add(cdo);
        }
        instance.setVariable("dataObjectsList", dataObjectsList);
        instance.businessKey("testBusinessKey");

        instance.execute();
    }

    @Override
    public void notifyEndProcess(String topic, String processKey, HandoverMessage handoverMessage) {

        messageProducer.sendEndProcessMessage(topic, handoverMessage);
        logger.info("Message End Process " + processKey + "sent to Atlas topic " + topic + " /n message: " + handoverMessage.toString() );

    }
}
