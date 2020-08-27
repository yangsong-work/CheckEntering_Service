package com.fri.pojo.bo.xicheng.response;

import com.fri.common.Location;

import java.util.Date;

public class CheckInfoListResponse extends BaseResponse {

    //核查地点
    private String checkAddress;
    private String disposalWay;
    //核查时间
    private Date checkTime;
    //经纬度
    private Location location;
    //核查人员姓名
    private String name;
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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "CheckInfoListResponse{" +
                "checkAddress='" + checkAddress + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", checkTime=" + checkTime +
                ", location=" + location +
                ", name='" + name + '\'' +
                ", checkObject='" + checkObject + '\'' +
                ", identify='" + identify + '\'' +
                '}';
    }
}
