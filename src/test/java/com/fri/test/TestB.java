package com.fri.test;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TestB {
    public static void main(String[] args) {
       LocalDateTime localDateTime =  LocalDateTime.now();
       DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
       String s  = localDateTime.format(dt);
        System.out.println(s);
    }
}
