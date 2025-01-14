package com.vasco.controllers;

import com.vasco.kafka.producer.MessageProducer;
import com.vasco.model.KafkaMessageModel;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.ProcessEngines;
import org.camunda.bpm.engine.runtime.ProcessInstantiationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {



    @Autowired
    private MessageProducer messageProducer;

    @GetMapping(value = "test")
    public String test(){
        return "Test Camunda Controller";
    }

    @GetMapping(value = "/execute")
    public String execute(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("Process_1dv384a");

        String item = "Test item from Controller";

        instance.setVariable("itemName", item);

        instance.businessKey("testBusinessKey");

        instance.executeWithVariablesInReturn();

        return "BPMN Executed";
    }


    @GetMapping(value = "/testKafka")
    public String testKafka(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("Process_1dv384a");

        String item = "Test item from Kafka endpoint";

        instance.setVariable("itemName", item);

        instance.execute();

        KafkaMessageModel message = new KafkaMessageModel(123, "testControllerv1.0", 1, 1);

        messageProducer.sendMessage("test-print-topic", message);

        JsonSerializer<KafkaMessageModel> serializer = new JsonSerializer<>();


        System.out.println("messageKafkaSerialized " + serializer.serialize("test-print-topic", message));

        return "BPMN Kafka Executed";
    }

    @GetMapping(value = "/testHttpConnectorGet")
    public String testHttpConnectorGet(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("Process_1dv384av2");

        instance.execute();

        return "BPMN Http Connector GET Executed";
    }

    @GetMapping(value = "/testHttpConnectorPost")
    public String testHttpConnectorPost(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstantiationBuilder instance = engine.getRuntimeService().createProcessInstanceByKey("Process_1dv384av3");

        instance.execute();

        return "BPMN Http Connector POST Executed";
    }

}
