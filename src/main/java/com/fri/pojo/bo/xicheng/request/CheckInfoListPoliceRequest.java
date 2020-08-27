package com.fri.pojo.bo.xicheng.request;

import java.util.Date;

public class CheckInfoListPoliceRequest {
    //核查对象(1、境内人员 2、境外人员 3、车辆)
    private String obj;

    //状态  0：未完成  1：已完成
    private String State;

    //核查开始时间
    private Date checkStartTime;

    //核查结束时间
    private Date checkEndTime;
}
