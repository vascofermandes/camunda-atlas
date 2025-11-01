package com.vasco.service.imp;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.Authentication;
import com.vasco.model.CamundaDataObject;
import com.vasco.model.DataObject;
import com.vasco.model.HandoverMessage;
import com.vasco.model.Variables;
import com.vasco.model.WorkflowEngine;
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

        // Basic variables
        instance.setVariable("processId", handoverMessage.getProcessId());
        instance.setVariable("processName", handoverMessage.getProcessName());
        instance.setVariable("versionName", handoverMessage.getVersionName());
        instance.setVariable("caseId", handoverMessage.getCaseId());

        // Variable used by BPMN script task
        instance.setVariable("itemName", handoverMessage.getProcessName());

        // Next workflow engine (null-safe)
        WorkflowEngine next = handoverMessage.getNextWorkflowEngine();
        if (next != null) {
            instance.setVariable("nextEndpoint", next.getEndpoint());
            Authentication auth = next.getAuthentication();
            if (auth != null) {
                instance.setVariable("nextAuthType", auth.getType());
                instance.setVariable("nextAuthToken", auth.getToken());
            }
        }

        // Variables (null-safe)
        Variables vars = handoverMessage.getVariables();
        if (vars != null) {
            instance.setVariable("nextVarInvNumber", vars.getInvoiceNumber());
            instance.setVariable("nextVarAmourt", vars.getAmount());
            instance.setVariable("nextVarCurrency", vars.getCurrency());
            instance.setVariable("nextVarCustId", vars.getCustomerId());
            instance.setVariable("nextVarAppStatus", vars.getApprovalStatus());
        }

        // Data objects (null-safe)
        List<CamundaDataObject> dataObjectsList = new ArrayList<>();
        List<DataObject> incomingDataObjects = handoverMessage.getDataObjects();
        if (incomingDataObjects != null) {
            for (DataObject dataObject : incomingDataObjects) {
                if (dataObject == null) continue;
                CamundaDataObject cdo = new CamundaDataObject();
                cdo.setId(dataObject.getId());
                cdo.setName(dataObject.getName());
                cdo.setType(dataObject.getType());
                cdo.setUrl(dataObject.getUrl());
                if (dataObject.getMetadata() != null) {
                    cdo.setMetadataSize(dataObject.getMetadata().getSize());
                    cdo.setMetadataUploadTime(dataObject.getMetadata().getUploadTime());
                    cdo.setMetadataUploadedBy(dataObject.getMetadata().getUploadedBy());
                }
                dataObjectsList.add(cdo);
            }
        }
        instance.setVariable("dataObjectsList", dataObjectsList);

        instance.businessKey("testBusinessKey");

        instance.execute();
    }

    @Override
    public void notifyEndProcess(String topic, String processKey, HandoverMessage handoverMessage) {
        handoverMessage.setProcessId(processKey);
        messageProducer.sendEndProcessMessage(topic, handoverMessage);
        logger.info("Message End Process " + processKey + "sent to Atlas topic " + topic + " /n message: " + handoverMessage.toString() );

    }
}
