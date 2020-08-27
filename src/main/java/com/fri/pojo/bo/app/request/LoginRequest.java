package com.fri.pojo.bo.app.request;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Value;

public class LoginRequest {
    //核录桩设备号，除PC外，其他设备必填
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    //警员padId
    @NotBlank(message = "警员padId不能为空")
    private String padId;
    //警员身份证号
    @NotBlank(message = "警员身份证号不能为空")
    private String policeIDCard;
    //警员姓名
    @NotBlank(message = "警员姓名不能为空")
    private String policeName;
    //警员编号
    @NotBlank(message = "警员编号不能为空")
    private String policeNumber;
    //警员单位
    @NotBlank(message = "警员单位不能为空")
    private String policeOrg;
    //经度坐标，除PC核录外，其他设备必填
    @NotBlank(message = "经度坐标不能为空")
    private String lon;
    //维度坐标，除PC核录外，其他设备必填
    @NotBlank(message = "纬度坐标不能为空")
    private String lat;

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

    public String getPoliceIDCard() {
        return policeIDCard;
    }

    public void setPoliceIDCard(String policeIDCard) {
        this.policeIDCard = policeIDCard;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getPoliceOrg() {
        return policeOrg;
    }

    public void setPoliceOrg(String policeOrg) {
        this.policeOrg = policeOrg;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", padId='" + padId + '\'' +
                ", policeIDCard='" + policeIDCard + '\'' +
                ", policeName='" + policeName + '\'' +
                ", policeNumber='" + policeNumber + '\'' +
                ", policeOrg='" + policeOrg + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
