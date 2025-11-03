package com.vasco.controllers;

import com.vasco.exception.MalformedObjectException;
import com.vasco.model.ProcessMonitorMessage;
import com.vasco.service.imp.ProcessMonitorServiceImp;
import com.vasco.util.ProcessMonitorMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitorConnector")
public class MonitorController {



    @Autowired
    private ProcessMonitorServiceImp processMonitorService;

    @Value("${kafka.monitor-topic}")
    private String testTopic;

    @PostMapping(value = "/{topic}/sendMessage")
    public String sendprocessMonitorMessage(@PathVariable("topic") String topic,
                                            @RequestBody ProcessMonitorMessage processMonitorMessage)
    {

        System.out.println("PRINT CONTROLLER RECEIVED REQUEST BODY " );
        System.out.println("Topic " + topic);
        System.out.println("EnvironmentId " + processMonitorMessage.getEnvironmentId());
        System.out.println("EnvironmentName " + processMonitorMessage.getEnvironmentName());
        System.out.println("processId " + processMonitorMessage.getProcessId());
        System.out.println("processName " + processMonitorMessage.getProcessName());
        System.out.println("versionId " + processMonitorMessage.getVersionId());
        System.out.println("type " + processMonitorMessage.getType());
        System.out.println("caseId " + processMonitorMessage.getCaseId());
        System.out.println("groupId " + processMonitorMessage.getGroupId());
        System.out.println("userId " + processMonitorMessage.getUserId());
        System.out.println("taskName " + processMonitorMessage.getTaskName());
        System.out.println("isStartProcess " + processMonitorMessage.isStartProcess());

        if(ProcessMonitorMessageValidator.isValidMessage(processMonitorMessage)){
            processMonitorService.send( topic, processMonitorMessage);
            return "Test Response OK";
        }
        throw(new MalformedObjectException("Malformed processMonitorMessage"));


    }

    @GetMapping(value = "/testSendMessage")
    public void sendTestprocessMonitorMessage(){

        ProcessMonitorMessage processMonitorMessage = new ProcessMonitorMessage();
        processMonitorMessage.setEnvironmentId(1);
        processMonitorMessage.setEnvironmentName("testCamundaTenantName");
        processMonitorMessage.setProcessId(1234);
        processMonitorMessage.setProcessName("testCamundaProcessName");
        processMonitorMessage.setVersionId("testCamundaVersionName");
        processMonitorMessage.setType("testCamundaType");
        processMonitorMessage.setCaseId(3124);
        processMonitorMessage.setGroupId("adminGroupId");
        processMonitorMessage.setUserId(34);
        processMonitorMessage.setTaskName("testCamundaTaskName");
        processMonitorMessage.setStartProcess(false);;


        if(ProcessMonitorMessageValidator.isValidMessage(processMonitorMessage)){
            processMonitorService.send(testTopic, processMonitorMessage);
            return;
        }
        throw(new MalformedObjectException("Malformed processMonitorMessage"));


    }



}
