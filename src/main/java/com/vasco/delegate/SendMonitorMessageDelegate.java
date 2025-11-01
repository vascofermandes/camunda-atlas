package com.vasco.delegate;

import com.vasco.exception.MalformedObjectException;
import com.vasco.model.ProcessMonitorMessage;
import com.vasco.service.imp.ProcessMonitorServiceImp;
import com.vasco.util.ProcessMonitorMessageValidator;
import com.vasco.util.DelegateSafeGets;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sendMonitorMessageDelegate")
public class SendMonitorMessageDelegate implements JavaDelegate {

    @Autowired
    private ProcessMonitorServiceImp processMonitorService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ProcessMonitorMessage processMonitorMessage = new ProcessMonitorMessage();

        processMonitorMessage.setCaseId(Integer.parseInt(execution.getProcessBusinessKey() != null ? execution.getProcessBusinessKey() : "defaultCaseId"));

        processMonitorMessage.setEnvironmentId(DelegateSafeGets.getSafeIntegerVariable(execution, "environmentId", 1));
        processMonitorMessage.setEnvironmentName(DelegateSafeGets.getSafeStringVariable(execution, "tenantName", "masterTenant"));
        processMonitorMessage.setProcessId(DelegateSafeGets.getSafeIntegerVariable(execution, "processId", 10214));
        processMonitorMessage.setProcessName(DelegateSafeGets.getSafeStringVariable(execution, "processName", "defaultProcessName"));
        processMonitorMessage.setVersionId(DelegateSafeGets.getSafeStringVariable(execution, "versionName", "defaultVersionName"));
        processMonitorMessage.setType(DelegateSafeGets.getSafeStringVariable(execution, "type", "defaultType"));
        processMonitorMessage.setGroupId(DelegateSafeGets.getSafeStringVariable(execution, "groupName", "defaultGroupName"));
        processMonitorMessage.setUserId(DelegateSafeGets.getSafeIntegerVariable(execution, "userName", 4));
        processMonitorMessage.setTaskName(DelegateSafeGets.getSafeStringVariable(execution, "taskName", "defaultTaskName"));
        processMonitorMessage.setStartProcess(DelegateSafeGets.getSafeBooleanVariable(execution, "isStartProcess", false));

        String topic = DelegateSafeGets.getSafeStringVariable(execution, "topic", "defaultTopic");

        System.out.println("🛠 ProcessMonitorMessage content:");
        System.out.println("Case ID: " + processMonitorMessage.getCaseId());
        System.out.println("Environment ID: " + processMonitorMessage.getEnvironmentId());
        System.out.println("Environment Name: " + processMonitorMessage.getEnvironmentName());
        System.out.println("Process ID: " + processMonitorMessage.getProcessId());
        System.out.println("Process Name: " + processMonitorMessage.getProcessName());
        System.out.println("Version ID: " + processMonitorMessage.getVersionId());
        System.out.println("Type: " + processMonitorMessage.getType());
        System.out.println("Group ID: " + processMonitorMessage.getGroupId());
        System.out.println("User ID: " + processMonitorMessage.getUserId());
        System.out.println("Task Name: " + processMonitorMessage.getTaskName());
        System.out.println("Is Start Process: " + processMonitorMessage.isStartProcess());
        System.out.println("TOPIC: " + topic);

//I am forcing "testTopic" topic to allow me to check if it arrives correctly to the kafka server using
        // my previously defined MessageConsumer Listener - you can use the variable topic
        if (ProcessMonitorMessageValidator.isValidMessage(processMonitorMessage)) {
            processMonitorService.send("testTopic", processMonitorMessage);
        } else {
            throw new MalformedObjectException("Malformed processMonitorMessage");
        }
    }
}
