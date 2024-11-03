package com.dancestudio.erp.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class StatusResponse implements Serializable {
    private static final long serialVersionUID = 5521850192852967435L;
    private int statusCode;
    private String statusMessage = "";
    private Type statusType;
    private long totalCount;

    public StatusResponse() {
        this.statusType = Type.SUCCESS;
    }

    public StatusResponse(int statusCode, String statusMessage, Type type, int totalCount) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.statusType = type;
        this.totalCount = totalCount;
    }

    public StatusResponse(int statusCode, String statusMessage, Type type) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.statusType = type;
    }

    public StatusResponse(int statusCode, Type type, int totalCount) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.statusType = type;
        this.totalCount = totalCount;
    }


    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return this.statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Type getStatusType() {
        return this.statusType;
    }

    public void setStatusType(Type statusType) {
        this.statusType = statusType;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }


    @JsonIgnore
    public boolean isSuccess() {
        return this.statusType == Type.SUCCESS;
    }

    public String toString() {
        return "StatusResponse [statusCode=" + this.statusCode + ", statusMessage=" + this.statusMessage + ", statusType=" + this.statusType + ", totalCount=" + this.totalCount + "]";
    }

    public enum Type {
        ERROR,
        SUCCESS,
        WARNING;

        Type() {
        }
    }
}
