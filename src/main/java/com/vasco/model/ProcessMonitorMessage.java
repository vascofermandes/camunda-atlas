package com.vasco.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessMonitorMessage {

    private int environmentId;
    private String tenantName;
    private String processId;
    private String processName;
    private String versionName;
    private String type;
    private String caseId;
    private String groupName;
    private String userName;
    private String taskName;
    private boolean isStartProcess;

}
