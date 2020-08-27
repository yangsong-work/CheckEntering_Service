package com.fri.pojo.bo.app.request;

import org.hibernate.validator.constraints.NotBlank;

public class LogoutRequest {
    //核录桩设备号，除PC外，其他设备必填
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    //警员padId
    @NotBlank(message = "警员padId不能为空")
    private String padId;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    @Override
    public String toString() {
        return "LogoutRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", padId='" + padId + '\'' +
                '}';
    }
}
