package com.fri.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fri.common.Result;
import com.fri.exception.NoMessageException;
import com.fri.model.PoliceLoginRecord;
import com.fri.pojo.bo.app.request.CheckPersonJsDetailRequest;
import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.pojo.bo.pinen.VerifyImageRequest;
import com.fri.pojo.bo.pinen.VerifyOcrRequest;
import com.fri.pojo.bo.xicheng.response.CheckPersonBasicInfoResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonFaceResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonPhotoResponse;
import com.fri.service.CheckEnterService;
import com.fri.service.XiChengService;
import com.fri.utils.UserUtil;
import com.fri.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;

@RestController
@RequestMapping("/check")
public class CheckEnterWebController {
    public static Logger log = LoggerFactory.getLogger(CheckEnterWebController.class);
    @Autowired
    CheckEnterService checkEnterService;

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    XiChengService xiChengService;
    /**
     * 人证核验+手工输入
     */
    @PostMapping("/idcard")
    public String verifyIDCard(@Valid @RequestBody VerifyIDCardRequest request) throws NoMessageException {
        log.info("接收核录桩身份证接口时间：{}",LocalDateTime.now());
        //设备号未绑定报错
        if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }
        log.info("核录桩传送身份证信息接收:{},{}", request.toString(), LocalDateTime.now());

        Map map = checkEnterService.verifyIDCard(request);
        log.info("核录桩传送身份证信息推送完毕:{}", LocalDateTime.now());
        if (map == null || map.isEmpty()) {
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(map);
    }

    @PostMapping("/face")
    public String verifyImage(@Valid @RequestBody VerifyImageRequest request) throws NoMessageException {
        log.info("接收人像相似接口时间：{}",LocalDateTime.now());
        //设备号未绑定报错
        if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }
        log.info("核录桩传送人脸信息接收:{},{}", request.toString(), LocalDateTime.now());
        Map map = checkEnterService.verifyFacePhoto(request);
        if (map == null || map.isEmpty()) {
            return ResponseUtil.fail("1","查无信息");
        }
        log.info("核录桩传送人脸信息推送完毕:{},{}", request.toString(), LocalDateTime.now());
        return ResponseUtil.ok();
    }


    //ocr证件查询
    @PostMapping("/ocr")
    public String verifyOcr(@Valid @RequestBody VerifyOcrRequest request) throws NoMessageException {
        log.info("接收OCR接口时间：{}",LocalDateTime.now());
        //设备号未绑定报错
        if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }
        log.info("核录桩传送OCR证件信息接收:{},{}", request.toString(), LocalDateTime.now());
        checkEnterService.verifyOcr(request);
        log.info("核录桩传送OCR证件信息推送完毕:{}", LocalDateTime.now());
        return ResponseUtil.ok();
    }
    /**
     * 离线上送身份证号
     */
    @PostMapping("/offLineupload")
    public String offLineUpload(@RequestBody Map map) throws NoMessageException {
        log.info("离线上送身份证号时间：{}",LocalDateTime.now());
        String deviceNo = (String) map.get("deviceNo");
        List<String> list = (List) map.get("cardList");
        if(StringUtils.isEmpty(deviceNo)||list==null||list.size()==0){
            return ResponseUtil.fail402();
        }
        checkEnterService.verifyIDCard4OffLine(deviceNo,list);
        //设备号未绑定报错
//        if (UserUtil.getUserMap().get(deviceNo) == null) {
//            return ResponseUtil.fail("1", "核录桩未绑定");
//        }
        return ResponseUtil.ok();
    }
    /**
     * 无数据 测试网络是否通畅
     */
    @PostMapping("/pingstatus")
    public String getPingstatus(){
        return ResponseUtil.ok();
    }
}
