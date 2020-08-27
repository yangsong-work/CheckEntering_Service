package com.fri.test;

import com.fri.model.CheckEnterPushInfo;

import java.time.LocalDateTime;

public class TestA {
    public static void main(String[] args) {
        CheckEnterPushInfo pushInfo = new CheckEnterPushInfo();
        pushInfo.setPadId("1234");
        pushInfo.setPoliceIDCard("43567");
        pushInfo.setCreatedTime(LocalDateTime.now());
        System.out.println(pushInfo.getGreenWarningNumber());
        System.out.println(pushInfo);


    }
}
