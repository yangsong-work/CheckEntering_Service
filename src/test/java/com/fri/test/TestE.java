package com.fri.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TestE {
    public static void main(String[] args) {
        LocalDateTime localDateTime =  LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String time  = localDateTime.format(dt);
        System.out.println(time);

        int i  = (int) (Math.random()*9000+1000);
        System.out.println(i);
    }
}
