package com.fri.pojo.bo.app.request;

import org.hibernate.validator.constraints.NotBlank;

public class CheckOptionRequest {
    //padId
    @NotBlank(message = "padId不能为空")
    private String padId;
    //核录桩设备号
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    //核查地点
    @NotBlank(message = "核查地点不能为空")
    private String checkAddress;
    //核查任务
    @NotBlank(message = "核查任务不能为空")
    private String checkTask;
    //核查事由
    @NotBlank(message = "核查事由不能为空")
    private String checkReason;

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(String checkTask) {
        this.checkTask = checkTask;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    @Override
    public String toString() {
        return "CheckOptionRequest{" +
                "padId='" + padId + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", checkAddress='" + checkAddress + '\'' +
                ", checkTask='" + checkTask + '\'' +
                ", checkReason='" + checkReason + '\'' +
                '}';
    }
}
