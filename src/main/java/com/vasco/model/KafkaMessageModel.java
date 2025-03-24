package com.vasco.model;

public class KafkaMessageModel {

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



    public KafkaMessageModel(){

    };

    public KafkaMessageModel(int environmentId, String tenantName, String processId, String processName, String versionName, String type, String caseId, String groupName, String userName, String taskName, boolean isStartProcess) {
        this.environmentId = environmentId;
        this.tenantName = tenantName;
        this.processId = processId;
        this.processName = processName;
        this.versionName = versionName;
        this.type = type;
        this.caseId = caseId;
        this.groupName = groupName;
        this.userName = userName;
        this.taskName = taskName;
        this.isStartProcess = isStartProcess;
    }

    public int getEnvironmentId() {
        return environmentId;
    }

    public void setEnvironmentId(int environmentId) {
        this.environmentId = environmentId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public boolean getIsStartProcess() {
        return isStartProcess;
    }

    public void setIsStartProcess(boolean isStartProcess) {
        this.isStartProcess = isStartProcess;
    }
}
