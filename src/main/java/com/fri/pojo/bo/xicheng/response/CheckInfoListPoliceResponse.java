package com.fri.pojo.bo.xicheng.response;

import java.util.Date;

public class CheckInfoListPoliceResponse extends BaseResponse {

    //核查地址
    private String checkAddress;
    //
    private String disposalWay;
    //核查时间
    private Date checkTime;
    //
    private String checkObject;
    //核查人员身份证号码
    private String identify;

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getDisposalWay() {
        return disposalWay;
    }

    public void setDisposalWay(String disposalWay) {
        this.disposalWay = disposalWay;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckObject() {
        return checkObject;
    }

    public void setCheckObject(String checkObject) {
        this.checkObject = checkObject;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    @Override
    public String toString() {
        return "CheckInfoListPoliceResponse{" +
                "checkAddress='" + checkAddress + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", checkTime=" + checkTime +
                ", checkObject='" + checkObject + '\'' +
                ", identify='" + identify + '\'' +
                '}';
    }
}
