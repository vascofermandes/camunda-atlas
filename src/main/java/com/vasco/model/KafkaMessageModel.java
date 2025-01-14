package com.vasco.model;

public class KafkaMessageModel {

    private int tenantId;
    private String version;

    private int userId;

    private int actionCode;

    public KafkaMessageModel(){

    };
    public KafkaMessageModel(int tenantId, String version, int userId, int actionCode) {
        this.tenantId = tenantId;
        this.version = version;
        this.userId = userId;
        this.actionCode = actionCode;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActionCode() {
        return actionCode;
    }

    public void setActionCode(int actionCode) {
        this.actionCode = actionCode;
    }
}
