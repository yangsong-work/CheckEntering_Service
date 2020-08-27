package com.fri.pojo.bo.pinen;

import org.hibernate.validator.constraints.NotBlank;

public class VerifyOcrRequest {

    //String核录桩设备号
    @NotBlank(message = "核录桩设备号不能为空")
    String deviceNo;

    //国籍
    @NotBlank(message = "国籍不能为空")
    String nationality;

    //证件类型
    @NotBlank(message = "证件类型不能为空")
    String cardType;

    // 证件号码
    @NotBlank(message = "证件号码不能为空")
    String cardNo;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @Override
    public String toString() {
        return "VerifyOcrRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", nationality='" + nationality + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
