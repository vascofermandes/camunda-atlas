package com.vasco.controllers;

import com.vasco.exception.MalformedObjectException;
import com.vasco.model.KafkaMessageModel;
import com.vasco.service.imp.AtlasCommunicationServiceImp;
import com.vasco.util.KafkaMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {



    @Autowired
    private AtlasCommunicationServiceImp atlasComunicationService;


    @PostMapping(value = "/atlas/sendKafkaMessage")
    public void sendKafkaMessage(@RequestBody KafkaMessageModel kafkaMessage){


        if(KafkaMessageValidator.isValidMessage(kafkaMessage)){
            atlasComunicationService.send(kafkaMessage);
            return;
        }
        throw(new MalformedObjectException("Malformed kafkaMessage"));


    }

    @GetMapping(value = "/atlas/sendTestKafkaMessage")
    public void sendTestKafkaMessage(){

        KafkaMessageModel kafkaMessage = new KafkaMessageModel();
        kafkaMessage.setEnvironmentId(123);
        kafkaMessage.setTenantName("testCamundaTenantName");
        kafkaMessage.setProcessId("testCamundaProcessId");
        kafkaMessage.setProcessName("testCamundaProcessName");
        kafkaMessage.setVersionName("testCamundaVersionName");
        kafkaMessage.setType("testCamundaType");
        kafkaMessage.setCaseId("testCamundaCaseId");
        kafkaMessage.setGroupName("testCamundaGroupName");
        kafkaMessage.setUserName("testCamundaUserName");
        kafkaMessage.setTaskName("testCamundaTaskName");
        kafkaMessage.setIsStartProcess(false);;


        if(KafkaMessageValidator.isValidMessage(kafkaMessage)){
            atlasComunicationService.send(kafkaMessage);
            return;
        }
        throw(new MalformedObjectException("Malformed kafkaMessage"));


    }



}
