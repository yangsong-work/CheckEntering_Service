package com.fri.test;

import com.fri.dao.CountryInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TestF {
    @Autowired
    CountryInfoMapper countryInfoMapper;
    public static void main(String[] args) throws IOException {
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter fdt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String s = fdt.format(time);
        System.out.println(s);
    }

}
