package com.fri.controller;

import com.alibaba.fastjson.JSONObject;
import com.fri.exception.NoMessageException;
import com.fri.pojo.bo.xicheng.request.CheckForeignPersonInfoRequest;
import com.fri.pojo.bo.xicheng.response.CheckForeignPersonBasicReponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonBasicInfoResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonJsResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonPhotoResponse;
import com.fri.service.impl.XiChengServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Deprecated
public class XiChengWebController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    XiChengServiceImpl xiChengService;
    @GetMapping("/testGetApi")
    public String getJson() {
        String url = "http://localhost:8888/common/test1";
        //String json =restTemplate.getForObject(url,Object.class);
        ResponseEntity<String> results = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
        String json = results.getBody();
        return json;
    }
    @PostMapping(value = "/testPostApi")
    public Object testPost() {
        String url = "http://localhost:8888/common/test2";
        JSONObject postData = new JSONObject();
        postData.put("descp", "request for post");
        JSONObject json = restTemplate.postForEntity(url, postData, JSONObject.class).getBody();
        return json;


    }
    @PostMapping(value = "/testCheckJs")
    public Object testCheckJs() {
         List res = xiChengService.checkPersonJs("111","222");
         return  res;

    }
    @PostMapping(value = "/testCheckBase")
    public Object testCheckBase() throws NoMessageException {
         CheckPersonBasicInfoResponse res = xiChengService.checkPersonBasicInfo("111","222");
         return  res;

    }@PostMapping(value = "/testCheckforeBase")
    public Object testCheckforeBase() throws NoMessageException {
        CheckForeignPersonInfoRequest request = new CheckForeignPersonInfoRequest();
        request.setZjhm("123131");
        CheckForeignPersonBasicReponse res = xiChengService.checkForeignPersonBasicInfo(request);
         return  res;
    }
    @PostMapping(value = "/testCheckforeJs")
    public Object testCheckforeJs() {
        CheckForeignPersonInfoRequest checkForeignPersonInfoRequest = new CheckForeignPersonInfoRequest();
        checkForeignPersonInfoRequest.setZjhm("123131");
        List res = xiChengService.checkForeignPersonJsInfo(checkForeignPersonInfoRequest);
        return  res;

    }
    @PostMapping(value = "/testCheckphoto")
    public Object testCheckphoto() {
        CheckPersonPhotoResponse res = xiChengService.checkPersonPhoto("134567","110105197007153531");
        return  res;

    }
}
