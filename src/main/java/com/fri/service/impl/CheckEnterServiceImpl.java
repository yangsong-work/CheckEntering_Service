package com.fri.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fri.common.Result;
import com.fri.contants.CommonContants;
import com.fri.dao.*;
import com.fri.exception.NoMessageException;
import com.fri.model.*;
import com.fri.pojo.bo.app.push.CheckInfo;
import com.fri.pojo.bo.app.push.FacePhoneInfo;
import com.fri.pojo.bo.app.request.APPUpdateRequest;
import com.fri.pojo.bo.app.request.CheckPersonJsDetailRequest;
import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.pojo.bo.pinen.VerifyImageRequest;
import com.fri.pojo.bo.pinen.VerifyOcrRequest;
import com.fri.pojo.bo.xicheng.request.CheckForeignPersonInfoRequest;
import com.fri.pojo.bo.xicheng.response.*;
import com.fri.service.APPService;
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
    APPService appService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    PoliceLoginRecordMapper policeLoginRecordMapper;
    @Autowired
    CheckEnterPushInfoMapper checkEnterPushInfoMapper;
    @Autowired
    CheckPeopleMapper checkPeopleMapper;
    @Autowired
    CheckImageMapper checkImageMapper;
    @Autowired
    CountryInfoMapper countryInfoMapper;
    @Value("${heluzhuang.url}")
    String heluzhuangUrl;
    @Value("${socket.url}")
    String socketUrl;
    @Value("${server.port}")
    String port;
    @Value("${xicheng.appid}")
    String appid;
    @Value("${xicheng.appType}")
    String appType;
    @Override
    public Map verifyIDCard(VerifyIDCardRequest verifyIDCardRequest) throws NoMessageException {
        //品恩核录桩抓取相片入库
        CheckImage checkImage = new CheckImage();
        checkImage.setImg(verifyIDCardRequest.getpCaptureImage());
        checkImage.setCardNo(verifyIDCardRequest.getpCardNo());
        //图片不存储
        //checkImageMapper.insertSelective(checkImage);

        String IDCard = verifyIDCardRequest.getpCardNo();
        // 人证核验 comparestatus为1时 表示已开启人证核验
        if (verifyIDCardRequest.getCheckFrom() == 0&&verifyIDCardRequest.getCompareStatus()==1) {
            String compareValue = verifyIDCardRequest.getCompareValue();
            //TODO 测试代码 上线删除
//            UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).setDeviceScore("60");
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
        List<CheckWarnInfo> checkWarnInfoList = new ArrayList<>();
        List<CheckPersonJs4XiCheng> personJsList4XiCheng = new ArrayList<>();
        try {
            Map jsXiChengMap = new HashMap();
            jsXiChengMap.put("cardId", verifyIDCardRequest.getpCardNo());
            jsXiChengMap.put("deviceNum", UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId());
            jsXiChengMap.put("policeNumber", UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPoliceNumber());
            personJsList4XiCheng = xiChengService.checkPersonJs4XiCheng(jsXiChengMap,"person");
            checkWarnInfoList = appService.transferList(personJsList4XiCheng);
            for (CheckWarnInfo personJs : checkWarnInfoList) {
                String color = personJs.getColor();
                if (warningColor.equals("red")||("red").equals(color)) {
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
            System.out.println("西城公安网数据"+checkWarnInfoList);
        }catch (Exception e){
            log.info("西城接口发送失败");
        }


        List<CheckPersonJs> list4XiCheng = new ArrayList<>();
        list4XiCheng = transferList(personJsList4XiCheng);
        list4XiCheng = myCompareList(list4XiCheng,"1");

        personJsList = myCompareList(personJsList,"0");

        list4XiCheng.addAll(personJsList);



        //组装push数据
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
        int resultStatus = checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);
        int age = LocalDateTime.now().getYear() - Integer.valueOf(personBasicInfoResponse.getCardNumber().substring(6, 10));

        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
       // checkInfo.setWarningNumber(pushInfo.getWarningNumber());
        //checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
        //checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
        //checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
        //checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setName(personBasicInfoResponse.getName());
        checkInfo.setSex(personBasicInfoResponse.getSex());
        checkInfo.setAge(age+"");
        checkInfo.setMinzuCn(personBasicInfoResponse.getMinzuCn());
        checkInfo.setHouseHolds(personBasicInfoResponse.getHouseHolds());
        checkInfo.setWarningColor(warningColor);
        checkInfo.setWarnList(list4XiCheng);
        checkInfo.setImg(personPhotoResponse.getZp());


        checkInfo.setCardNumber(personBasicInfoResponse.getCardNumber());
        checkInfo.setWarnList(list4XiCheng);
        checkInfo.setGuoJi(personBasicInfoResponse.getGuoJi());

        CheckPeople checkPeople = new CheckPeople();
        checkPeople.setIdCard(IDCard);
        if(warningColor.equals("white")) checkPeople.setColor(0);
        else if(warningColor.equals("green")) checkPeople.setColor(1);
        else if(warningColor.equals("yellow")) checkPeople.setColor(2);
        else if(warningColor.equals("red")) checkPeople.setColor(3);
        checkPeople.setCompareStatus(verifyIDCardRequest.getCompareStatus());
        checkPeople.setPid(pushInfo.getId());
        checkPeopleMapper.insertPeople(checkPeople);
        //推送至PAD
        Map pushMap = new HashMap();
        pushMap.put("messageType", 1);
        pushMap.put("data", checkInfo);
        System.out.println(JSON.toJSONString(pushMap));
        boolean flag = pushMessage(verifyIDCardRequest.getDeviceNo(), "idcard", pushMap, CommonContants.IDCARD_METHOD);
        //  socketUtil.sendMessage(MyUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));
        // 发送至二类区服务

        //TODO 测试代码 上线删除
        if (!flag) {
            log.info("向pad推送失敗");
            throw new RuntimeException();
        }
        //TODO 自动录入 上线加上
        if(!warningColor.equalsIgnoreCase("yellow")&&!warningColor.equalsIgnoreCase("red")) {
            APPUpdateRequest req = new APPUpdateRequest();
            req.setCheckObject("1");
            req.setCheckResult("未发现可疑情况，排除通行");
            req.setDeviceNo(verifyIDCardRequest.getDeviceNo());
            req.setDisposalWay("1");
            req.setIdentify(verifyIDCardRequest.getpCardNo());
            appService.upLoad(req);
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
    public Object verifyOcr(VerifyOcrRequest ocrRequest) throws NoMessageException {

        ocrRequest.setNationality(countryInfoMapper.selectCountryEn(ocrRequest.getNationality()));

        CheckForeignPersonInfoRequest infoRequest = new CheckForeignPersonInfoRequest();
        infoRequest.setGj(ocrRequest.getNationality());
        infoRequest.setZjlb(ocrRequest.getCardType());
        infoRequest.setZjhm(ocrRequest.getCardNo());
        infoRequest.setDeviceNo(ocrRequest.getDeviceNo());

        CheckForeignPersonBasicReponse checkForeignPersonBasic = xiChengService.checkForeignPersonBasicInfo(infoRequest);
        List<CheckPersonJs> checkForeignPersonJsList = xiChengService.checkForeignPersonJsInfo(infoRequest);

        PoliceLoginRecord record = UserUtil.getUserMap().get(ocrRequest.getDeviceNo());
        String padId = record.getPadId();
        CheckEnterPushInfo pushInfo = UserUtil.getPadPushMap().get(padId);

        Set<String> set = new HashSet<String>();
        String warningColor = "white";
        String status = "1";
        for (CheckPersonJs personJs : checkForeignPersonJsList) {
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

        //西城警示接口
        List<CheckWarnInfo> checkWarnInfoList = new ArrayList<>();
        List<CheckPersonJs4XiCheng> personJsList4XiCheng = new ArrayList<>();
        try {
            Map jsXiChengMap = new HashMap();
            jsXiChengMap.put("cardId", ocrRequest.getCardNo());
            jsXiChengMap.put("deviceNum", UserUtil.getUserMap().get(ocrRequest.getDeviceNo()).getPadId());
            jsXiChengMap.put("policeNumber", UserUtil.getUserMap().get(ocrRequest.getDeviceNo()).getPoliceNumber());
            personJsList4XiCheng = xiChengService.checkPersonJs4XiCheng(jsXiChengMap,"");
            checkWarnInfoList = appService.transferList(personJsList4XiCheng);
            for (CheckWarnInfo personJs : checkWarnInfoList) {
                String color = personJs.getColor();
                if (warningColor.equals("red")||("red").equals(color)) {
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
            System.out.println("西城公安网数据"+checkWarnInfoList);
        }catch (Exception e){
            log.info("西城接口发送失败");
        }


        List<CheckPersonJs> list4XiCheng = new ArrayList<>();
        list4XiCheng = transferList(personJsList4XiCheng);
        list4XiCheng = myCompareList(list4XiCheng,"1");

        checkForeignPersonJsList = myCompareList(checkForeignPersonJsList,"0");

        list4XiCheng.addAll(checkForeignPersonJsList);



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

        int age = LocalDateTime.now().getYear() - Integer.valueOf(checkForeignPersonBasic.getBirthDay().substring(0, 4));

        //更新push表的信息
        pushInfo.setWarningNumber(pushInfo.getGreenWarningNumber() + pushInfo.getYellowWarningNumber() + pushInfo.getRedWarningNumber());
        pushInfo.setUpdatedTime(LocalDateTime.now());
        checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);


        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
//        checkInfo.setWarningNumber(pushInfo.getWarningNumber());
//        checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
//        checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
//        checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
//        checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setName(checkForeignPersonBasic.getForeignerName());
        checkInfo.setSex(checkForeignPersonBasic.getSex());
        checkInfo.setAge(age+"");
        checkInfo.setMinzuCn("");
        checkInfo.setHouseHolds("");
        checkInfo.setWarningColor(warningColor);
        checkInfo.setImg("");
        checkInfo.setCardNumber(checkForeignPersonBasic.getIdentify());
        checkInfo.setWarnList(list4XiCheng);
        checkInfo.setGuoJi(checkForeignPersonBasic.getGuoJi());
        //护照插入
        CheckPeople checkPeople = new CheckPeople();
        checkPeople.setIdCard(ocrRequest.getCardNo());
        if(warningColor.equals("white")) checkPeople.setColor(0);
        else if(warningColor.equals("green")) checkPeople.setColor(1);
        else if(warningColor.equals("yellow")) checkPeople.setColor(2);
        else if(warningColor.equals("red")) checkPeople.setColor(3);
        checkPeople.setCompareStatus(0);
        checkPeople.setPid(pushInfo.getId());
        checkPeopleMapper.insertPeople(checkPeople);
        //推送至PAD
        Map pushMap = new HashMap();
        pushMap.put("messageType", 1);
        pushMap.put("data", checkInfo);
        boolean flag = pushMessage(ocrRequest.getDeviceNo(), "ocr", pushMap, CommonContants.OCR_METHOD);
        // socketUtil.sendMessage(MyUtil.getUserMap().get(ocrRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));
        // 发送至二类区服务
        System.out.println(JSON.toJSONString(pushMap));
        if (!flag) {
            log.info("向pad推送失敗");
            throw new RuntimeException();
        }
        //TODO 自动录入  上线解开
        if(!warningColor.equalsIgnoreCase("yellow")&&!warningColor.equalsIgnoreCase("red")) {
        APPUpdateRequest req = new APPUpdateRequest();

            req.setCheckObject("2");
            String type = "ocr";
            req.setCheckResult("未发现可疑情况，排除通行");
            req.setDeviceNo(ocrRequest.getDeviceNo());
            req.setDisposalWay("1");
            req.setIdentify(ocrRequest.getCardNo());
            appService.upLoad(req);
        }

        Map returMap = new HashMap();
        returMap.put("status", status);
        returMap.put("deviceNo", ocrRequest.getDeviceNo());
        return returMap;
    }

    public Map verifyFacePhoto(VerifyImageRequest request) throws NoMessageException {
        String img = request.getImg();
        String deviceNo = request.getDeviceNo();
        PoliceLoginRecord record = UserUtil.getUserMap().get(deviceNo);
        HashMap<String, String> hashMap = new HashMap<String, String>();
        JSONObject json = new JSONObject();
        json.put("appid", appid);
        json.put("deviceid", deviceNo);
        json.put("policesfzh", record.getPoliceIDCard());
        json.put("policename", record.getPoliceName());
        json.put("policeorg", record.getPoliceOrg());
        json.put("apptype", appType);
        json.put("lon", record.getLon());
        json.put("lat", record.getLat());
        json.put("img", img);
        Map returnMap = new HashMap<>();
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> requestEntity = new HttpEntity<String>(json.toString(), requestHeaders);
            String data = restTemplate.postForEntity("http://14.28.2.32:8080/helu/checkPersonFace", requestEntity, String.class).getBody();
            log.info("总线返回报文：{}", data);
            returnMap = JSON.parseObject(data, Map.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        log.info("总线返回报文：{}", returnMap.toString());
        List<JSONObject> JSONObjects = (List) returnMap.get("results");
        List<FacePhoneInfo> facePhoneInfos = new ArrayList<>();
      //  List<CheckPersonFaceResponse> CheckPersonFaceResponseList = new ArrayList<>();
        for (JSONObject jsonObject : JSONObjects) {
            CheckPersonFaceResponse checkPersonFaceResponse = JSON.parseObject(jsonObject.toString(), CheckPersonFaceResponse.class);
         //   CheckPersonFaceResponseList.add(checkPersonFaceResponse);
            String idnumber = checkPersonFaceResponse.getIdnumber();
            CheckPersonBasicInfoResponse checkPersonBasicInfoResponse = xiChengService.checkPersonBasicInfo(idnumber, deviceNo);
            CheckPersonPhotoResponse checkPersonPhotoResponse = xiChengService.checkPersonPhoto(deviceNo,idnumber);
            if(checkPersonPhotoResponse!=null) {
                FacePhoneInfo facePhoneInfo = new FacePhoneInfo();
                facePhoneInfo.setCardNumber(checkPersonBasicInfoResponse.getCardNumber());
                facePhoneInfo.setName(checkPersonBasicInfoResponse.getName());
                facePhoneInfo.setImg(checkPersonPhotoResponse.getZp());
                facePhoneInfo.setSimilarityDegree(checkPersonFaceResponse.getSimilaritydegree());
                facePhoneInfo.setGuoJi(checkPersonBasicInfoResponse.getGuoJi());
                facePhoneInfos.add(facePhoneInfo);
            }
        }
        //根据相似度从大大小排序
        for (int i = 0; i < facePhoneInfos.size(); i++) {
            int k = i;
            for (int j = i + 1; j < facePhoneInfos.size(); j++) {

                if ((Double.parseDouble(facePhoneInfos.get(j).getSimilarityDegree())) >
                        (Double.parseDouble(facePhoneInfos.get(k).getSimilarityDegree()))) {
                    k = j;
                }
            }
            if (i != k) {
                FacePhoneInfo facePhoneInfo1 = facePhoneInfos.get(i);
                facePhoneInfos.set(i, facePhoneInfos.get(k));
                facePhoneInfos.set(k, facePhoneInfo1);
            }
        }
        //推送至PAD
        Map<String,Object> pushMap = new HashMap<String,Object>();
        pushMap.put("messageType", 2);
        pushMap.put("data", facePhoneInfos);
        boolean flag = pushMessage(request.getDeviceNo(), "face", pushMap, "FUN003");

        // 发送至二类区服务
        System.out.println(JSON.toJSONString(pushMap));
        if (!flag) {
            log.info("向pad推送失敗");
            throw new RuntimeException();
        }
        Map returMap = new HashMap();
        // returMap.put("status", status);
         returMap.put("deviceNo", request.getDeviceNo());
        return returMap;
       // return null;
    }


    /**
     * 人证核验未通过流程
     */
    private Map queryInfo(String IDCard, VerifyIDCardRequest verifyIDCardRequest) throws NoMessageException {
        System.out.println("人证核验未通过流程");
        CheckPersonBasicInfoResponse personBasicInfoResponse = xiChengService.checkPersonBasicInfo(IDCard, verifyIDCardRequest.getDeviceNo());
        CheckPersonPhotoResponse personPhotoResponse = xiChengService.checkPersonPhoto(verifyIDCardRequest.getDeviceNo(), IDCard);

        PoliceLoginRecord record = UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo());
        String padId = record.getPadId();
        CheckEnterPushInfo pushInfo = UserUtil.getPadPushMap().get(padId);
        pushInfo.setVerifyNumber(pushInfo.getVerifyNumber() + 1);
        pushInfo.setUpdatedTime(LocalDateTime.now());

        int age = LocalDateTime.now().getYear() - Integer.valueOf(personBasicInfoResponse.getCardNumber().substring(6, 10));





        //更新push表
        checkEnterPushInfoMapper.updateByPrimaryKeySelective(pushInfo);
        //组合推送信息
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCheckNumber(pushInfo.getCheckNumber());
//        checkInfo.setWarningNumber(pushInfo.getWarningNumber());
//        checkInfo.setRedWarningNumber(pushInfo.getRedWarningNumber());
//        checkInfo.setYellowWarningNumber(pushInfo.getYellowWarningNumber());
//        checkInfo.setGreenWarningNumber(pushInfo.getGreenWarningNumber());
//        checkInfo.setVerifyNumber(pushInfo.getVerifyNumber());
        checkInfo.setName(personBasicInfoResponse.getName());
        checkInfo.setSex(personBasicInfoResponse.getSex());
        checkInfo.setAge(age+"");
        checkInfo.setMinzuCn(personBasicInfoResponse.getMinzuCn());
        checkInfo.setHouseHolds(personBasicInfoResponse.getHouseHolds());
        checkInfo.setWarningColor("orange");
        checkInfo.setImg(personPhotoResponse.getZp());
        checkInfo.setCardNumber(personBasicInfoResponse.getCardNumber());
        //人证核验未通过 没确定
        checkInfo.setWarnList(new ArrayList());
        checkInfo.setGuoJi(personBasicInfoResponse.getGuoJi());
        Map pushMap = new HashMap();
        //推送PAD
        pushMap.put("messageType", 3);
        pushMap.put("data", checkInfo);
        boolean flag = pushMessage(verifyIDCardRequest.getDeviceNo(), "idcard", pushMap, CommonContants.IDCARD_METHOD);
        //trsUtil.sendMessage(UserUtil.getUserMap().get(verifyIDCardRequest.getDeviceNo()).getPadId(), JSON.toJSONString(pushMap));

        if (!flag) {
            log.info("向pad推送失敗");
            throw new RuntimeException();
        }




        Map returnMap = new HashMap();
        returnMap.put("status", "2");
        returnMap.put("deviceNo", verifyIDCardRequest.getDeviceNo());
        return returnMap;
    }

    //通知核录桩登录功能

    public void notifyLogin(String padId) {
        String deviceNo = policeLoginRecordMapper.selectDeviceNoByPadId(padId);
        String data = "";
        Map request = new HashMap();
        request.put("deviceNo", deviceNo);
        request.put("account",UserUtil.getUserMap().get(deviceNo).getPoliceNumber());
        request.put("userName",UserUtil.getUserMap().get(deviceNo).getPoliceName());
        request.put("photo","");
        String paramString = JSON.toJSONString(request);
        log.info("服务器推送数据时间：{}",LocalDateTime.now());
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
            throw new RuntimeException();
        }
        Map dataMap = (Map) map.get("data");
        Integer status = (Integer) dataMap.get("status");
        if (status == 1) {
            log.info("核录桩登录成功");
        } else {
            log.info("核录桩登录失败: {}" , status);
            throw new RuntimeException();
        }
        String verifyScore = (String) dataMap.get("verifyScore");
        if (!StringUtils.isBlank(verifyScore)) {
            policeLoginRecordMapper.updateVerifyScore(padId, deviceNo, verifyScore);
        }
        UserUtil.getUserMap().get(deviceNo).setDeviceScore(verifyScore);
        log.info("推送数据完毕：{}",LocalDateTime.now());
    }

    /**
     * 离线上传身份证号
     * @param deviceNo
     * @param cardList
     * @return
     */
    @Override
    public void verifyIDCard4OffLine(String deviceNo, List<String> cardList) throws NoMessageException {

        for (String IDCard : cardList) {
            boolean flag = true;
            CheckPersonBasicInfoResponse personBasicInfoResponse = xiChengService.checkPersonBasicInfo(IDCard, deviceNo);
            List<CheckPersonJs> personJsList = xiChengService.checkPersonJs(IDCard, deviceNo);
           if(personJsList!=null&&personJsList.size()!=0){
                for (CheckPersonJs checkPersonJs: personJsList){
                    if (checkPersonJs.getColor().equalsIgnoreCase("yellow")||checkPersonJs.getColor().equalsIgnoreCase("red")){
                        flag = false;
                        break;
                    }
                }
           }
            List<CheckPersonJs4XiCheng>  personJsList4XiCheng = new ArrayList();
        try {
            Map jsXiChengMap = new HashMap();
            jsXiChengMap.put("cardId", IDCard);
            jsXiChengMap.put("deviceNum", UserUtil.getUserMap().get(deviceNo).getPadId());
            jsXiChengMap.put("policeNumber", UserUtil.getUserMap().get(deviceNo).getPoliceNumber());
            personJsList4XiCheng = xiChengService.checkPersonJs4XiCheng(jsXiChengMap,"person");

        }catch (Exception e){
            log.info("西城接口发送失败");
        }
            if(personJsList4XiCheng!=null||personJsList4XiCheng.size()!=0){
                for (CheckPersonJs4XiCheng checkPersonJs: personJsList4XiCheng){
                    if (checkPersonJs.getALARM_LEVEL().equals("2")||checkPersonJs.getALARM_LEVEL().equals("1")){
                        flag = false;
                        break;
                    }
                }
            }
            //TODO 自动录入 注释掉 上线放开
         if(flag){
             //录入
                APPUpdateRequest req = new APPUpdateRequest();
                req.setCheckObject("1");
                req.setCheckResult("未发现可疑情况，排除通行");
                req.setDeviceNo(deviceNo);
                req.setDisposalWay("1");
                req.setIdentify(IDCard);
                appService.upLoad(req);
         }
        }


    }

    /**
     * 推送至二类区
     * @param deviceNo
     * @param method
     * @param mapData
     * @param FFBS
     * @return
     */
    public boolean pushMessage(String deviceNo, String method, Map mapData, String FFBS) {
        //TODO 测试代码
//        String url = socketUrl+method;

        String url = socketUrl;
        String padId = UserUtil.getUserMap().get(deviceNo).getPadId();

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


        sendMap.put("XXCZRY_XM", UserUtil.getUserMap().get(deviceNo).getPoliceName());
        sendMap.put("XXCZRY_GMSFHM", UserUtil.getUserMap().get(deviceNo).getPoliceIDCard());
        sendMap.put("XXCZRY_GAJGJGDM", UserUtil.getUserMap().get(deviceNo).getPoliceOrg());
        sendMap.put("FWQQSB_BH", SERVICE_IP);
        sendMap.put("FWQQ_SJSJLX", "service_request");


        Map returnMap = new HashMap<>();
        try {
//            HttpHeaders requestHeaders = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            requestHeaders.setContentType(type);
//            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
//            HttpEntity<Map> requestEntity = new HttpEntity<Map>(dataMap, requestHeaders);
            log.info("总线发送报文：{}", sendMap);
            String data = restTemplate.postForObject(url,sendMap, String.class);
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
    private List myCompareList(List<CheckPersonJs> list,String isLocal) {
        List<CheckPersonJs> returnList = new ArrayList<>();
        //按红黄绿paixv
        List<CheckPersonJs> yellowList = new ArrayList<>();
        List<CheckPersonJs> greenList = new ArrayList<>();
        for (CheckPersonJs checkPersonJs : list) {
            checkPersonJs.setIsLocal(isLocal);
            if (checkPersonJs.getColor().equals("red")) {
                returnList.add(checkPersonJs);
            }
            if (checkPersonJs.getColor().equals("yellow")) {
                yellowList.add(checkPersonJs);
            }
            if (checkPersonJs.getColor().equals("green")) {
                greenList.add(checkPersonJs);
            }
        }
        returnList.addAll(yellowList);
        returnList.addAll(greenList);
        return returnList;
    }

    //西城警示转换公安3.0警示
    private List<CheckPersonJs> transferList(List<CheckPersonJs4XiCheng> list) {
        List<CheckPersonJs> returnList = new ArrayList<>();
        for (CheckPersonJs4XiCheng checkPersonJs4XiCheng : list) {
            CheckPersonJs checkPersonJs = new CheckPersonJs();
            checkPersonJs.setValue(checkPersonJs4XiCheng.getDATA_CLASS());
            checkPersonJs.setResourceName(checkPersonJs4XiCheng.getResourceName());

            String color = "white";
            switch (checkPersonJs4XiCheng.getALARM_LEVEL()) {
                case "3":
                    color = "green";
                    break;
                case "2":
                    color = "yellow";
                    break;
                case "1":
                    color = "red";
                    break;
            }
            checkPersonJs.setColor(color);
            returnList.add(checkPersonJs);
        }
        return  returnList;
    }
}
