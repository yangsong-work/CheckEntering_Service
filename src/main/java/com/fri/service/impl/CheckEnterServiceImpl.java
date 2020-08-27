package com.fri.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fri.common.Result;
import com.fri.contants.CommonContants;
import com.fri.dao.CheckEnterPushInfoMapper;
import com.fri.dao.CheckImageMapper;
import com.fri.dao.PoliceLoginRecordMapper;
import com.fri.model.CheckEnterPushInfo;
import com.fri.model.CheckImage;
import com.fri.model.CheckPersonJs;
import com.fri.model.PoliceLoginRecord;
import com.fri.pojo.bo.app.push.CheckInfo;
import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.pojo.bo.pinen.VerifyOcrRequest;
import com.fri.pojo.bo.xicheng.request.CheckForeignPersonInfoRequest;
import com.fri.pojo.bo.xicheng.response.*;
import com.fri.service.CheckEnterService;
import com.fri.service.XiChengService;
import com.fri.utils.UserUtil;
import com.fri.utils.TrsUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.fri.contants.CommonContants.*;

@Service
public class CheckEnterServiceImpl implements CheckEnterService {
    public static Logger log = LoggerFactory.getLogger(CheckEnterService.class);
    @Autowired
    XiChengService xiChengService;
    @Autowired
    TrsUtil trsUtil;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PoliceLoginRecordMapper policeLoginRecordMapper;
    @Autowired
    CheckEnterPushInfoMapper checkEnterPushInfoMapper;
    @Autowired
    CheckImageMapper checkImageMapper;
    @Value("${heluzhuang.url}")
    String heluzhuangUrl;
    @Value("${socket.url}")
    String socketUrl;
    @Value("${server.port}")
    String port;

    @Override
    public Map verifyIDCard(VerifyIDCardRequest verifyIDCardRequest) {
        //品恩核录桩抓取相片入库
        CheckImage checkImage = new CheckImage();
        checkImage.setImg(verifyIDCardRequest.getpCaptureImage());
        checkImage.setCardNo(verifyIDCardRequest.getpCardNo());
        checkImageMapper.insertSelective(checkImage);

        String IDCard = verifyIDCardRequest.getpCardNo();
        // 人证核验
        if (verifyIDCardRequest.getCheckFrom() == 0&&verifyIDCardRequest.getCompareStatus()==1) {
            String compareValue = verifyIDCardRequest.getCompareValue();
            //TODO 测试代码 上线删除
           // UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).setDeviceScore("60");
            String score = UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getDeviceScore();
            int compareResult = compareValue.compareTo(score);
            if (compareResult < 0) {
                Map map = queryInfo(IDCard, verifyIDCardRequest);
                return map;
            }
        }
        //人证核验通过流程与手工输入身份证号流程
        CheckPersonBasicInfoResponse personBasicInfoResponse = xiChengService.checkPersonBasicInfo(IDCard, verifyIDCardRequest.getDeviceNo());
        List<CheckPersonJs> personJsList = xiChengService.checkPersonJs(IDCard, verifyIDCardRequest.getDeviceNo());
        CheckPersonPhotoResponse personPhotoResponse = xiChengService.checkPersonPhoto(verifyIDCardRequest.getDeviceNo(), IDCard);
        //组装接口
        PoliceLoginRecord record = UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo());
        String padId = record.getPadId();
        CheckEnterPushInfo pushInfo = UserUtil.getPadPushMap().get(padId);

        Set<String> set = new HashSet<String>();
        String warningColor = "white";
        String status = "1";
        for (CheckPersonJs personJs : personJsList) {
            String color = personJs.getColor();
            if (("red").equals(color)) {
                warningColor = color;
                status = "2";
                break;
            }
            if (("yellow").equals(color)) {
                warningColor = color;
                status = "2";
            }
            if (("green").equals(color) && !warningColor.equals("yellow")) {
                warningColor = color;
                status = "1";
            }
        }

        pushInfo.setPoliceIDCard(record.getPoliceIDCard());
        pushInfo.setCheckNumber(pushInfo.getCheckNumber() + 1);

        switch (warningColor) {
            case "red":
                pushInfo.setRedWarningNumber(pushInfo.getRedWarningNumber() + 1);
                break;
            case "yellow":
                pushInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber() + 1);
                break;
            case "green":
                pushInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber() + 1);
                break;
        }

        //更新push表的信息
        pushInfo.setWarningNumber(pushInfo.getGreenWarningNumber() + pushInfo.getYellowWarningNumber() + pushInfo.getRedWarningNumber());
        pushInfo.setUpdatedTime(LocalDateTime.now());
        checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);


        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
        checkInfo.setWarningNumber(pushInfo.getWarningNumber());
        checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
        checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
        checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
        checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setWarningColor(warningColor);
        checkInfo.setImg(personPhotoResponse.getZp());
        checkInfo.setCardNumber(personBasicInfoResponse.getCardNumber());

        //推送至PAD
        Map pushMap = new HashMap();
        pushMap.put("messageType", 1);
        pushMap.put("data", JSON.toJSON(checkInfo));
        boolean flag = pushMessage(UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), "idcard", pushMap, CommonContants.IDCARD_METHOD);
        //  socketUtil.sendMessage(MyUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));
        //TODO 发送至二类区服务
        System.out.println(JSON.toJSONString(pushMap));
        if (!flag) {
            throw new RuntimeException();
        }
        Map returMap = new HashMap();
        returMap.put("status", status);
        returMap.put("deviceNo", verifyIDCardRequest.getDeviceNo());
        return returMap;


    }

    /**
     * ocr验证外国人护照
     *
     * @param ocrRequest
     * @return
     */
    @Override
    public Object verifyOcr(VerifyOcrRequest ocrRequest) {

        Properties p = new Properties();
        ocrRequest.setNationality(p.getProperty(ocrRequest.getNationality()));

        CheckForeignPersonInfoRequest infoRequest = new CheckForeignPersonInfoRequest();
        infoRequest.setGj(ocrRequest.getNationality());
        infoRequest.setZjlb(ocrRequest.getCardType());
        infoRequest.setZjhm(ocrRequest.getCardNo());
        infoRequest.setDeviceNo(ocrRequest.getDeviceNo());

        CheckForeignPersonBasicReponse checkForeignPersonBasic = xiChengService.checkForeignPersonBasicInfo(infoRequest);
        List<CheckForeignPersonJsReponse> checkForeignPersonJsList = xiChengService.checkForeignPersonJsInfo(infoRequest);

        PoliceLoginRecord record = UserUtil.getUserMap().get(ocrRequest.getDeviceNo());
        String padId = record.getPadId();
        CheckEnterPushInfo pushInfo = UserUtil.getPadPushMap().get(padId);

        Set<String> set = new HashSet<String>();
        String warningColor = "white";
        String status = "1";
        for (CheckForeignPersonJsReponse personJs : checkForeignPersonJsList) {
            String color = personJs.getColor();
            if (("red").equals(color)) {
                warningColor = color;
                status = "2";
                break;
            }
            if (("yellow").equals(color)) {
                warningColor = color;
                status = "2";
            }
            if (("green").equals(color) && !warningColor.equals("yellow")) {
                warningColor = color;
                status = "1";
            }
        }

        pushInfo.setPoliceIDCard(record.getPoliceIDCard());
        pushInfo.setCheckNumber(pushInfo.getCheckNumber() + 1);

        switch (warningColor) {
            case "red":
                pushInfo.setRedWarningNumber(pushInfo.getRedWarningNumber() + 1);
                break;
            case "yellow":
                pushInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber() + 1);
                break;
            case "green":
                pushInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber() + 1);
                break;
        }

        //更新push表的信息
        pushInfo.setWarningNumber(pushInfo.getGreenWarningNumber() + pushInfo.getYellowWarningNumber() + pushInfo.getRedWarningNumber());
        pushInfo.setUpdatedTime(LocalDateTime.now());
        checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);


        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
        checkInfo.setWarningNumber(pushInfo.getWarningNumber());
        checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
        checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
        checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
        checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setWarningColor(warningColor);
        checkInfo.setImg("");
        checkInfo.setCardNumber(checkForeignPersonBasic.getIdentify());
        //TODO 人员信息入库
        //推送至PAD
        Map pushMap = new HashMap();
        pushMap.put("messageType", 1);
        pushMap.put("data", checkInfo);
        boolean flag = pushMessage(UserUtil.getUserMap().get(ocrRequest.getDeviceNo()).getPadId(), "ocr", pushMap, CommonContants.OCR_METHOD);
        // socketUtil.sendMessage(MyUtil.getUserMap().get(ocrRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));
        //TODO 发送至二类区服务
        System.out.println(JSON.toJSONString(pushMap));
        if (!flag) {
            throw new RuntimeException();
        }
        Map returMap = new HashMap();
        returMap.put("status", status);
        returMap.put("deviceNo", ocrRequest.getDeviceNo());
        return returMap;
    }

    public Result verifyFacePhoto() {
        return null;
    }


    /**
     * 人证核验未通过流程
     */
    private Map queryInfo(String IDCard, VerifyIDCardRequest verifyIDCardRequest) {
        CheckPersonBasicInfoResponse personBasicInfoResponse = xiChengService.checkPersonBasicInfo(IDCard, verifyIDCardRequest.getDeviceNo());
        CheckPersonPhotoResponse personPhotoResponse = xiChengService.checkPersonPhoto(verifyIDCardRequest.getDeviceNo(), IDCard);

        PoliceLoginRecord record = UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo());
        String padId = record.getPadId();
        CheckEnterPushInfo pushInfo = UserUtil.getPadPushMap().get(padId);
        pushInfo.setVerifyNumber(pushInfo.getVerifyNumber() + 1);
        pushInfo.setUpdatedTime(LocalDateTime.now());

        //更新push表
        checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);
        //组合推送信息
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
        checkInfo.setWarningNumber(pushInfo.getWarningNumber());
        checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
        checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
        checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
        checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setWarningColor("orange");
        checkInfo.setImg(personPhotoResponse.getZp());
        checkInfo.setCardNumber(personBasicInfoResponse.getCardNumber());
        Map pushMap = new HashMap();
        //推送PAD
        pushMap.put("messageType", 3);
        pushMap.put("data", checkInfo);
        boolean flag = pushMessage(UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), "idcard", pushMap, CommonContants.IDCARD_METHOD);
        //trsUtil.sendMessage(UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));
        //TODO 返回值
        if (!flag) {
            throw new RuntimeException();
        }
        Map returnMap = new HashMap();
        return returnMap;
    }

    //通知核录桩登录功能

    //TODO  思考要不要返回值
    public void notifyLogin(String padId) {
        String deviceNo = policeLoginRecordMapper.selectDeviceNoByPadId(padId);
        String data = "";
        Map request = new HashMap();
        request.put("deviceNo", deviceNo);
        String paramString = JSON.toJSONString(request);
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            HttpEntity<String> requestEntity = new HttpEntity<String>(paramString, requestHeaders);
            ResponseEntity postForEntity = restTemplate.exchange(heluzhuangUrl, HttpMethod.POST, requestEntity, String.class);
            data = (String) postForEntity.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map map = JSONObject.parseObject(data, Map.class);
        if ( 200 !=((Integer)map.get("code"))) {
            log.info("核录桩登录失败: {}" , map.get("code"));
        }
        Map dataMap = (Map) map.get("data");
        Integer status = (Integer) dataMap.get("status");
        if (status == 1) {
            log.info("核录桩登录成功");
        } else {
            log.info("核录桩登录失败: {}" , status);
        }
        String verifyScore = (String) dataMap.get("verifyScore");
        if (!StringUtils.isBlank(verifyScore)) {
            policeLoginRecordMapper.updateVerifyScore(padId, deviceNo, verifyScore);
        }
        UserUtil.getUserMap().get(deviceNo).setDeviceScore(verifyScore);
    }

    /**
     * 推送至二类区
     * @param padId
     * @param method
     * @param mapData
     * @param FFBS
     * @return
     */
    public boolean pushMessage(String padId, String method, Map mapData, String FFBS) {
        String url = socketUrl;
        Map dataMap = new HashMap();
        dataMap.put("padId", padId);
        dataMap.put("json", mapData);
        //拼装总线参数

        Map sendMap = new HashMap();

        LocalDateTime localDateTime = LocalDateTime.now();
        String time = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));

        int i = (int) (Math.random() * 9000 + 1000);
        String FWQQ_BWBH = "SR" + SERVICE_IP + time + i;
        sendMap.put("FWQQ_BWBH", FWQQ_BWBH);
        sendMap.put("BWLYIPDZ", SERVICE_IP);
        sendMap.put("BWLYDKH", port);
        sendMap.put("FWQQZ_ZCXX", FWQQZ_ZCXX);
        sendMap.put("FWBS", FWBS);
        sendMap.put("FFBS", FFBS);
        sendMap.put("FWQQ_RQSJ", localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        //方法及内容字段包装
        Map jsonMap = new HashMap();
        jsonMap.put("method", method);
        jsonMap.put("params", dataMap);

        sendMap.put("FWQQ_NR", jsonMap);

        //TODO 总线参数目前写死
        sendMap.put("XXCZRY_XM", "白志斌");
        sendMap.put("XXCZRY_GMSFHM", "140603199011271011");
        sendMap.put("XXCZRY_GAJGJGDM", "110102110000");
        sendMap.put("XXCZRY_BH", SERVICE_IP);
        sendMap.put("XXCZRY_SJSJLX", "service_request");


        Map returnMap = new HashMap<>();
        try {
//            HttpHeaders requestHeaders = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            requestHeaders.setContentType(type);
//            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
//            HttpEntity<Map> requestEntity = new HttpEntity<Map>(dataMap, requestHeaders);
            String data = restTemplate.postForObject(url, sendMap, String.class);
            log.info("总线返回报文：{}", data);
            returnMap = JSON.parseObject(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("二类区服务返回数据：{}", returnMap.toString());
        Map subMap = (Map) returnMap.get("FWTG_NR");
        if (subMap == null) {
            return false;
        } else {
            return subMap.get("code").equals("0");
        }
    }
}
