package com.fri.test;

import com.fri.dao.CountryInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Properties;

public class TestF {
    @Autowired
    CountryInfoMapper countryInfoMapper;
    public static void main(String[] args) throws IOException {
        int i = 0;
             try {
                 int b  = 2/0;
             }catch (NullPointerException e){
                 e.printStackTrace();
             }
        System.out.println("hello");
    }

}
