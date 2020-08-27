package com.fri.pojo.bo.xicheng.request;

import java.util.Date;

public class CheckInfoListRequest {
    //类别 1 中国人，2外国人，3车辆
    private String obj;

    //证件类型（lb=2,3时必填，填号牌种类或外国人证件类别）
    private String lb;

    //号码（填身份证号，护照号，车牌号）
    private String hm;

    //核查开始时间
    private Date checkStartTime;

    //核查结束时间
    private Date checkEndTime;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getHm() {
        return hm;
    }

    public void setHm(String hm) {
        this.hm = hm;
    }

    public Date getCheckStartTime() {
        return checkStartTime;
    }

    public void setCheckStartTime(Date checkStartTime) {
        this.checkStartTime = checkStartTime;
    }

    public Date getCheckEndTime() {
        return checkEndTime;
    }

    public void setCheckEndTime(Date checkEndTime) {
        this.checkEndTime = checkEndTime;
    }

    @Override
    public String toString() {
        return "CheckInfoListRequestVO{" +
                "obj='" + obj + '\'' +
                ", lb='" + lb + '\'' +
                ", hm='" + hm + '\'' +
                ", checkStartTime=" + checkStartTime +
                ", checkEndTime=" + checkEndTime +
                '}';
    }
}
