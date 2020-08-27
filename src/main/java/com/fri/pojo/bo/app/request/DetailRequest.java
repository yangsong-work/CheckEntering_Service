package com.fri.pojo.bo.app.request;

import org.hibernate.validator.constraints.NotBlank;

public class DetailRequest {
    //核录桩设备号，除PC外，其他设备必填
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    //警员padId
    @NotBlank(message = "警员padId不能为空")
    private String padId;
    //预警人员身份证号
    @NotBlank(message = "预警人员身份证号不能为空")
    private String cardNumber;

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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "DetailRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", padId='" + padId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }
}
