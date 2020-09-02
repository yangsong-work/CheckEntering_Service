package com.fri.pojo.bo.app.request;

import org.hibernate.validator.constraints.NotBlank;

public class APPUpdateRequest {
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    //处置方式 1-排除；2-存疑；3-留置
    @NotBlank(message = "处置方式不能为空")
    private String disposalWay;
    //核查结论
    @NotBlank(message = "核查结论不能为空")
    private String checkResult;
    //证件号码
    @NotBlank(message = "证件号码不能为空")
    private String identify;
    //核查对象类型（1-境内；2-境外；3-车辆）
    @NotBlank(message = "核查对象类型不能为空")
    private String checkObject;
    private String phoneNumber;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDisposalWay() {
        return disposalWay;
    }

    public void setDisposalWay(String disposalWay) {
        this.disposalWay = disposalWay;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getCheckObject() {
        return checkObject;
    }

    public void setCheckObject(String checkObject) {
        this.checkObject = checkObject;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "APPUpdateRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", checkResult='" + checkResult + '\'' +
                ", identify='" + identify + '\'' +
                ", checkObject='" + checkObject + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
