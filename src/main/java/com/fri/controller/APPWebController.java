package com.fri.controller;

import com.fri.model.PoliceInfo;
import com.fri.pojo.bo.app.request.*;
import com.fri.service.APPService;
import com.fri.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class APPWebController {
    private static Logger log = LoggerFactory.getLogger(APPWebController.class);
    @Autowired
    APPService appService;

    /**
     * 警员扫码绑定
     */
    @PostMapping(value = "/login")
    public String login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("警员扫码绑定：{}", loginRequest.toString());
        Integer result = appService.login(loginRequest);
        if (result == 1) {
            log.info("警员扫码绑定：{}", "成功");
            return ResponseUtil.ok();
        } else {
            log.info("警员扫码绑定：{}", "失败");
            return ResponseUtil.fail("1", "绑定失败");
        }
    }

    /**
     * 警员核查设置
     */
    @PostMapping(value = "/checkoption")
    public Object checkOption(@Valid @RequestBody CheckOptionRequest checkOptionRequest) {
        log.info("警员核查设置：{}", checkOptionRequest.toString());
        int i = appService.checkOption(checkOptionRequest);
        if (i == 1) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }
    }

    /**
     * 请求核查地
     */

    @PostMapping(value = "/checkaddress")
    public String getCheckAddress(@RequestBody Map<String, String> map) {
        String parentId = map.get("parentId");
        String deviceNo = map.get("deviceNo");
        if (parentId == null || StringUtils.isBlank(deviceNo)) {
            return ResponseUtil.fail402();
        }
        log.info("请求核查地：parentId = {}", parentId);
        List list = appService.getCheckAddress(deviceNo, parentId);
        if (list == null || list.isEmpty()) {
            return ResponseUtil.fail("1", "查询失败");
        }
        return ResponseUtil.ok(list);

    }

    /**
     * 登出（解绑）接口
     *
     * @param
     * @return
     */
    @PostMapping(value = "/logout")
    public Object logout(@Valid @RequestBody LogoutRequest logoutRequest) {
        log.info("警员解除绑定：{}", logoutRequest.toString());
        Integer result = appService.logout(logoutRequest);
        System.out.println(result);
        if (result == 1) {
            log.info("警员解除绑定：{}", "成功");
            return ResponseUtil.ok();
        } else {
            log.info("警员解除绑定：{}", "失败");
            return ResponseUtil.fail("1", "解绑失败");
        }
    }

    /**
     * 预警人员详细信息
     */
    @PostMapping(value = "/detail")
    public Object getDetails(@Valid @RequestBody DetailRequest detailRequest) {
        log.info("查询预警人员详细信息：{}", detailRequest.toString());
        Map map = appService.getDetails(detailRequest);
        return ResponseUtil.ok(map);
    }
    /*
     *   根据警号查询警员信息
     * */
    @PostMapping(value = "/checkpoliceInfo")
    public Object getCheckPoliceInfo(@RequestBody PoliceInfo policeInfo) {
        log.info("查询警号："+policeInfo.getUserAccount());
     /*   log.info("查询预警人员详细信息：{}", detailRequest.toString());
        Map map = appService.getDetails(detailRequest);
        return ResponseUtil.ok(map);*/
     //deviceNo    身份证号      resname
        PoliceManRequest policeManRequest = appService.getPoliceMessage(policeInfo.getUserAccount());
        if(policeManRequest==null){
            return ResponseUtil.fail();
        }
        return ResponseUtil.ok(policeManRequest);
    }
    /*
     *   请求人的警示详细信息
     * */
        @PostMapping(value = "/checkPersonJsDetail")
    public Object getPersonJsDetail(@RequestBody CheckPersonJsDetailRequest request) {
        //设备号未绑定报错
        /*if (UserUtil.getUserMap().get(request.getDeviceNo()) == null) {
            return ResponseUtil.fail("1", "核录桩未绑定");
        }*/
        log.info("核录桩传送警示详细信息接收:{},{}", request.toString(), LocalDateTime.now());
        Map map = appService.CheckPersonJsDetail(request);
        if (map == null || map.isEmpty()) {
            return ResponseUtil.fail();
        }
        log.info("核录桩传送警示详细信息:{},{}", request.toString(), LocalDateTime.now());
        return  ResponseUtil.ok(map);
    }
    /**
     * 录入
     */

    @RequestMapping("/exclude")
    public String insertExclude() {
        return "";
    }


    /**
     * 信息统计
     */

    @RequestMapping("/statistics")
    public String countStatistics() {
        return "";
    }
}
