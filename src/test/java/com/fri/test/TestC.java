package com.fri.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TestC {
    @Autowired
    RestTemplate restTemplate;
    @Value("${xicheng.url}")
    String url ;

    public static void main(String[] args) {
        TestC c = new TestC();
        c.test();
    }
    private void test(){
                String url = "http://14.28.2.32:8080/helu";
                UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("msisdn", "msisdn")
                .queryParam("sex", "男")
                .queryParam("national", "中国")
                .queryParam("birthday", "199002190");
      String a = builder.build().toUri().toString();
        System.out.println(a);
    }
}
