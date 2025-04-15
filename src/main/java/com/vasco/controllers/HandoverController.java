package com.vasco.controllers;

import com.vasco.exception.MalformedObjectException;
import com.vasco.model.HandoverMessage;
import com.vasco.service.imp.HandoverServiceImp;
import com.vasco.util.HandoverMessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/handover")
public class HandoverController {



    @Autowired
    private HandoverServiceImp handoverService;


    @PostMapping(value = "/{processKey}/startProcess")
    public ResponseEntity<String> startProcessHandover(@PathVariable("processKey") String processKey,
                                                       @RequestBody HandoverMessage handoverMessage) {

        System.out.println("HANDOVER CONTROLLER START PROCESS : " + processKey );

        if(HandoverMessageValidator.isValidMessage(handoverMessage)){
            handoverService.startProcess(processKey, handoverMessage);
            return ResponseEntity.ok("Process " + processKey + " started with success");
        }
        else {
            throw(new MalformedObjectException("Malformed processMonitorMessage"));
        }

    }

    @PostMapping(value = "/testStartProcess")
    public ResponseEntity testStartProcessHandover(@RequestBody HandoverMessage handoverMessage) {

        System.out.println("HANDOVER *TEST START PROCESS : " + "Process_1dv384a" );

        if(HandoverMessageValidator.isValidMessage(handoverMessage)){
            handoverService.startProcess("Process_1dv384a", handoverMessage);
            return ResponseEntity.ok("Process " + "Process_1dv384a" + " started with success");
        }
        else {
            throw(new MalformedObjectException("Malformed processMonitorMessage"));
        }
    }

    @PostMapping(value = "/{processKey}/notifyEndProcess")
    public ResponseEntity<String> notifyEndProcess(@PathVariable("processKey") String processKey,
                                                       @RequestBody HandoverMessage handoverMessage) {

        System.out.println(" CONTROLLER NOTIFY END PROCESS : " + processKey );

        if(HandoverMessageValidator.isValidMessage(handoverMessage)){
            handoverService.notifyEndProcess("handoverTopic", processKey, handoverMessage);
            return ResponseEntity.ok("Process end " + processKey + " notification sent to kafka");
        }
        else {
            throw(new MalformedObjectException("Malformed processMonitorMessage"));
        }

    }

    @PostMapping(value = "/testNotifyEndProcess")
    public ResponseEntity<String> testNotifyEndProcess(@RequestBody HandoverMessage handoverMessage) {

        System.out.println(" CONTROLLER NOTIFY END PROCESS : " + "Process_1dv384a" );

        if(HandoverMessageValidator.isValidMessage(handoverMessage)){
            handoverService.notifyEndProcess("handoverTopic", "Process_1dv384a", handoverMessage);
            return ResponseEntity.ok("Process end " + "Process_1dv384a" + " notification sent to kafka");
        }
        else {
            throw(new MalformedObjectException("Malformed processMonitorMessage"));
        }

    }


}
