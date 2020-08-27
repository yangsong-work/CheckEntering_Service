package com.fri.pojo.bo.app.response;

import java.util.List;
import java.util.Map;

public class DetailsResponse {
    private String checkArea;
    private String checkChannel;
    private String warnTime;
    private String name;
    private String sex;
    private String age;
    private String cardNumber;
    private String houseHolds;
    private String minzu;
    private String zp;

    public String getCheckArea() {
        return checkArea;
    }

    public void setCheckArea(String checkArea) {
        this.checkArea = checkArea;
    }

    public String getCheckChannel() {
        return checkChannel;
    }

    public void setCheckChannel(String checkChannel) {
        this.checkChannel = checkChannel;
    }

    public String getWarnTime() {
        return warnTime;
    }

    public void setWarnTime(String warnTime) {
        this.warnTime = warnTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(String houseHolds) {
        this.houseHolds = houseHolds;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    @Override
    public String toString() {
        return "DetailsResponse{" +
                "checkArea='" + checkArea + '\'' +
                ", checkChannel='" + checkChannel + '\'' +
                ", warnTime='" + warnTime + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", houseHolds='" + houseHolds + '\'' +
                ", minzu='" + minzu + '\'' +
                ", zp='" + zp + '\'' +
                '}';
    }
}

