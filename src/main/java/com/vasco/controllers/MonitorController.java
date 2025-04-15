package com.vasco.controllers;

import com.vasco.exception.MalformedObjectException;
import com.vasco.model.ProcessMonitorMessage;
import com.vasco.service.imp.ProcessMonitorServiceImp;
import com.vasco.util.ProcessMonitorMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/monitorConnector")
public class MonitorController {



    @Autowired
    private ProcessMonitorServiceImp processMonitorService;


    @PostMapping(value = "/{topic}/sendMessage")
    public String sendprocessMonitorMessage(@PathVariable("topic") String topic,
                                            @RequestBody ProcessMonitorMessage processMonitorMessage)
    {

        System.out.println("PRINT CONTROLLER RECEIVED REQUEST BODY " );
        System.out.println("Topic " + topic);
        System.out.println("EnvironmentId " + processMonitorMessage.getEnvironmentId());
        System.out.println("tenantName " + processMonitorMessage.getTenantName());
        System.out.println("processId " + processMonitorMessage.getProcessId());
        System.out.println("processName " + processMonitorMessage.getProcessName());
        System.out.println("versionName " + processMonitorMessage.getVersionName());
        System.out.println("type " + processMonitorMessage.getType());
        System.out.println("caseId " + processMonitorMessage.getCaseId());
        System.out.println("groupName " + processMonitorMessage.getGroupName());
        System.out.println("userName " + processMonitorMessage.getUserName());
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
        processMonitorMessage.setEnvironmentId(123);
        processMonitorMessage.setTenantName("testCamundaTenantName");
        processMonitorMessage.setProcessId("testCamundaProcessId");
        processMonitorMessage.setProcessName("testCamundaProcessName");
        processMonitorMessage.setVersionName("testCamundaVersionName");
        processMonitorMessage.setType("testCamundaType");
        processMonitorMessage.setCaseId("testCamundaCaseId");
        processMonitorMessage.setGroupName("testCamundaGroupName");
        processMonitorMessage.setUserName("testCamundaUserName");
        processMonitorMessage.setTaskName("testCamundaTaskName");
        processMonitorMessage.setStartProcess(false);;


        if(ProcessMonitorMessageValidator.isValidMessage(processMonitorMessage)){
            processMonitorService.send("testTopic", processMonitorMessage);
            return;
        }
        throw(new MalformedObjectException("Malformed processMonitorMessage"));


    }



}
