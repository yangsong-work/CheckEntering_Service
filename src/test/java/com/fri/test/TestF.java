package com.fri.test;

import com.fri.dao.CountryInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class TestF {
    public static void main(String[] args) {
        System.out.println("aaaa");
        try {
            throw new MyExcepton();
        } catch (MyExcepton myExcepton) {
            myExcepton.printStackTrace();
        }
    }

}
