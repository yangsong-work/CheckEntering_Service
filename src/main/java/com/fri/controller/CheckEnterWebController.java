package com.fri.controller;

import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.pojo.bo.pinen.VerifyImageRequest;
import com.fri.pojo.bo.pinen.VerifyOcrRequest;
import com.fri.service.CheckEnterService;
import com.fri.utils.UserUtil;
import com.fri.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/check")
public class CheckEnterWebController {
    public static Logger log = LoggerFactory.getLogger(CheckEnterWebController.class);
    @Autowired
    CheckEnterService checkEnterService;

    /**
     * 人证核验+手工输入
     */
    @PostMapping("/idcard")
    public String verifyIDCard(@Valid @RequestBody VerifyIDCardRequest request) {
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

    //TODO 人像相似列表请求 接口有问题
    @PostMapping("/face")
    public String verifyImage(@Valid @RequestBody VerifyImageRequest request) {
        //设备号未绑定报错
        if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }
        log.info("核录桩传送身份证信息:{},{}", request.toString(), LocalDateTime.now());
        return ResponseUtil.ok();
    }

    //ocr证件查询
    @PostMapping("/ocr")
    public String verifyOcr(@Valid @RequestBody VerifyOcrRequest request) {
        //设备号未绑定报错
        if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }
        log.info("核录桩传送OCR证件信息接收:{},{}", request.toString(), LocalDateTime.now());
        checkEnterService.verifyOcr(request);
        log.info("核录桩传送OCR证件信息推送完毕:{}", LocalDateTime.now());
        return ResponseUtil.ok();
    }


}
