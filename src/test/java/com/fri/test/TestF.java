package com.fri.test;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

public class TestF {
    public static void main(String[] args) throws IOException {
        FileInputStream f = new FileInputStream("classpath:resources/application-prod.properties");



        Properties p = new Properties();
        p.load(f);
        String s  = p.getProperty("日本");
        System.out.println(s);
    }
}
