package com.vasco.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ProcessMonitorMessage {

    private int environmentId;
    private String environmentName;
    private int processId;
    private String processName;
    private String versionId;
    private String type;
    private int caseId;
    private String groupId;
    private int userId;
    private String taskName;
    private boolean isStartProcess;

}
