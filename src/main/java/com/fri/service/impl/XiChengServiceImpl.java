package com.fri.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fri.dao.CheckInfoForeignMapper;
import com.fri.dao.CheckInfoMapper;
import com.fri.dao.CheckWarnInfoMapper;
import com.fri.dao.Test1Mapper;
import com.fri.exception.NoMessageException;
import com.fri.model.*;
import com.fri.pojo.bo.app.request.APPUpdateRequest;
import com.fri.pojo.bo.app.request.CheckPersonJsDetailRequest;
import com.fri.pojo.bo.xicheng.request.*;
import com.fri.service.XiChengService;
import com.fri.pojo.bo.xicheng.response.*;
import com.fri.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 警示信息分为西城和公安3.0 一方有数据则不报错
 */
@Service
public class XiChengServiceImpl implements XiChengService {
    private static Logger logger = LoggerFactory.getLogger(XiChengServiceImpl.class);
    @Autowired
    RestTemplate restTemplate;
    @Value("${xicheng.appid}")
    String appid;
    @Value("${xicheng.appType}")
    String appType;
    @Value("${xicheng.url}")
    String baseUrl;
    @Autowired
    Test1Mapper test1Mapper;
    @Autowired
    CheckInfoMapper checkInfoMapper;
    @Autowired
    CheckWarnInfoMapper checkWarnInfoMapper;
    @Autowired
    CheckInfoForeignMapper checkInfoForeignMapper;

    @Override
    public List<CheckInfoHistoryResponse> checkInfoHistory(CheckInfoHistoryResquest data) {
        return null;
    }

    @Override
    public CheckPersonBasicInfoResponse checkPersonBasicInfo(String IDCard, String deviceNo) throws NoMessageException {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckPersonBasicInfo");
        builder.queryParam("sfzh", IDCard);
        String url = builder.build().toUri().toString();
        String result = restTemplateForGet(url);
        JSONObject o = JSON.parseObject(result);
        if(o.getString("results")==null||o.getString("results").equals("")){
                throw new NoMessageException();
        }
        CheckPersonBasicInfoResponse checkPersonBasicInfoResponse = JSON.parseArray(o.getString("results"), CheckPersonBasicInfoResponse.class).get(0);
        CheckInfo checkInfo = new CheckInfo();
        BeanUtils.copyProperties(checkPersonBasicInfoResponse,checkInfo);
        checkInfoMapper.insertUser(checkInfo);
        return checkPersonBasicInfoResponse;
    }

    /**
     * 此方法可能返回空list，使用注意判空
     * @param IDCard
     * @param deviceNo
     * @return
     */
    @Override
    public List<CheckPersonJs> checkPersonJs(String IDCard, String deviceNo)  {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckPersonJs");
        builder.queryParam("sfzh", IDCard);
        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);

        List<CheckPersonJs> returnList = new ArrayList<>();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回空list
        if (map == null || !(map.get("status").toString().equals("0"))) {
           // throw new NoMessageException();
            logger.info("公安3.0接口报错");
            return  returnList;
        }
        JSONObject o = JSON.parseObject(data);
        if(o.getString("results").equals("")||o.getString("results")==null){
            //throw new NoMessageException();
            logger.info("公安3.0接口数据为空");
            return  returnList;
        }
        returnList = JSON.parseArray(o.getString("results"), CheckPersonJs.class);
        if(returnList==null||returnList.isEmpty()){
            logger.info("公安3.0接口数据为空");
            return  returnList;
        }
        //先删除再插入
        checkWarnInfoMapper.deleteByCardNumber(IDCard);
        List<CheckWarnInfo> list = new ArrayList<>();
        for(CheckPersonJs checkPersonJs:returnList){
        CheckWarnInfo checkWarnInfo = new CheckWarnInfo();
             BeanUtils.copyProperties(checkPersonJs,checkWarnInfo);
             checkWarnInfo.setCardNumber(IDCard);
             list.add(checkWarnInfo);
        checkWarnInfoMapper.insertBatch(list);
        }
        return returnList;
    }

    /**
     * 公安3.0警示详细
     * @param request
     * @return
     */
    @Override
    public List<CheckPersonJsDetail2> checkPersonJsDetail(CheckPersonJsDetailRequest request) throws NoMessageException {
        //拼接url
        UriComponentsBuilder builder = createBaseUri(request.getDeviceNo(), baseUrl + "CheckPersonJsDetail");
        builder.queryParam("sfzh", request.getCardNumber());
        builder.queryParam("resname", request.getResName());
        String url = builder.build().toUri().toString();
        System.out.println(url);
        //获取的json信息
        String data = restTemplateForGet(url);
      //  List<CheckPersonJs> returnList = new ArrayList<>();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return null;
        }
        if(map.get("results").toString().equals("")||map.get("results").toString()==null){
       //     throw new NoMessageException();
        }
        Map results = JSON.parseObject(map.get("results").toString(), Map.class);
        if(results==null){
         //   throw new NoMessageException();
        }
        Object dataset = results.get("dataset");
        if(dataset.toString().equals("")||data.toString()==null){
                throw new NoMessageException();
        }
       List<CheckPersonJsDetail> returnList = JSON.parseArray(dataset.toString(), CheckPersonJsDetail.class);
        List<CheckPersonJsDetail2> returnList2 = new ArrayList<>();
        for(CheckPersonJsDetail checkPersonJsDetail:returnList){
            CheckPersonJsDetail2 checkPersonJsDetail2 = new CheckPersonJsDetail2();
            checkPersonJsDetail2.setResource(checkPersonJsDetail.getResource());
            HashMap<String,String> hashMap =(HashMap) returnList.get(0).getRecord();
            StringBuffer s =new StringBuffer();
            Set<Map.Entry<String, String>> entries = hashMap.entrySet();
            for(Map.Entry<String,String> entry:entries)
                s.append(entry.getKey()+":"+entry.getValue()+"\n");
            checkPersonJsDetail2.setRecord(s.toString());
            returnList2.add(checkPersonJsDetail2);
        }
        return  returnList2;
    }

    /**
     * 西城公安警示详细信息
     * @param request
     * @return
     */
    @Override
    public List<CheckPersonJsDetail2> checkLocalJsDetail(CheckPersonJsDetailRequest request) {
        JSONObject jsonObject =new JSONObject();
        JSONObject jsonObject1 =new JSONObject();
        JSONObject jsonObject2 =new JSONObject();
        jsonObject1.put("method","/qbfx/checkV2/getPersonJsDetail");
        jsonObject1.put("params",jsonObject2);
        jsonObject2.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI4YThiYjU1MDVkNWUwM2E0MDE1ZWI3ZGQ5MmY2MDE2ZCIsImltZWkiOiI4Njk2NjEwMjAzMDk3OTQiLCJpYXQiOjE1ODg4MTQwMjA2MjksIm9yZ2lkIjoiMTEwMTAyNzYwMDAwIiwiYWNjb3VudCI6Ijg4ODgwMyIsInVybCI6IjEwLjExLjUzLjIwNSJ9.Zgkj88ha4Wn3yxM8eSS7_B6aumneqfjAaNLldG5vC3Q");
        jsonObject2.put("sfzh",request.getCardNumber());
        jsonObject2.put("resname",request.getResName());
        jsonObject.put("FFBS","FUN001");
        jsonObject.put("FWQQ_NR",jsonObject1);
        Map returnMap = new HashMap<>();
        List<CheckPersonJsDetail2> returnList2 = new ArrayList<>();
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonObject.toString(), requestHeaders);
            String data = restTemplate.postForEntity("http://10.11.53.207:7979/terminal/request", requestEntity, String.class).getBody();
            returnMap = JSON.parseObject(data, Map.class);
            Map results = JSON.parseObject(returnMap.get("FWTG_NR").toString(), Map.class);
            Object data1 = results.get("data");
            Map map = JSON.parseObject(data1.toString(), Map.class);
            if(map == null||map.get("data")==null){
                    throw new NoMessageException();
            }
            Set<Map.Entry<String, String>> entries = map.entrySet();
            StringBuffer s =new StringBuffer();
            for(Map.Entry<String,String> entry:entries)
                s.append(entry.getKey()+":"+entry.getValue()+"\n");
            CheckPersonJsDetail2 checkPersonJsDetail2 = new CheckPersonJsDetail2();
            checkPersonJsDetail2.setResource(request.getResName());
            checkPersonJsDetail2.setRecord(s.toString());
            returnList2.add(checkPersonJsDetail2);
        } catch (RuntimeException | NoMessageException e) {
            e.printStackTrace();
        }
        return returnList2;
    }

    @Override
    public CheckForeignPersonBasicReponse checkForeignPersonBasicInfo(CheckForeignPersonInfoRequest request) throws NoMessageException {
        UriComponentsBuilder builder = createBaseUri(request.getDeviceNo(), baseUrl + "CheckForeignPersonBasicInfo");
        builder.queryParam("gj", request.getGj());
        builder.queryParam("zjlb", request.getZjlb());
        builder.queryParam("zjhm", request.getZjhm());

        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);


        JSONObject o = JSON.parseObject(data);
        if(!o.getString("").equals("0")){
                throw new NoMessageException();
        }
        CheckForeignPersonBasicReponse checkForeignPersonBasicReponse = JSON.parseArray(o.getString("results"), CheckForeignPersonBasicReponse.class).get(0);

        CheckInfoForeign checkInfo = new CheckInfoForeign();
        BeanUtils.copyProperties(checkForeignPersonBasicReponse,checkInfo);
        checkInfo.setCardNumber(request.getZjhm());
        checkInfoForeignMapper.insertUser(checkInfo);
        return checkForeignPersonBasicReponse;
    }

    @Override
    public List<CheckForeignPersonJsReponse> checkForeignPersonJsInfo(CheckForeignPersonInfoRequest request) {
                UriComponentsBuilder builder = createBaseUri(request.getDeviceNo(), baseUrl + "CheckForeignPersonJsInfo");
        builder.queryParam("gj", request.getGj());
        builder.queryParam("zjlb", request.getZjlb());
        builder.queryParam("zjhm", request.getZjhm());
        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);


        List<CheckForeignPersonJsReponse> returnList = new ArrayList<>();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回空list
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return returnList;
        }
        JSONObject o = JSON.parseObject(data);
        returnList = JSON.parseArray(o.getString("results"), CheckForeignPersonJsReponse.class);

        //先删除再插入
        checkWarnInfoMapper.deleteByCardNumber(request.getZjhm());
        List<CheckWarnInfo> list = new ArrayList<>();
        for(CheckForeignPersonJsReponse reponse:returnList){
            CheckWarnInfo checkWarnInfo = new CheckWarnInfo();
            BeanUtils.copyProperties(reponse,checkWarnInfo);
            checkWarnInfo.setCardNumber(request.getZjhm());
            list.add(checkWarnInfo);
        }
        checkWarnInfoMapper.insertBatch(list);
        return returnList;
    }

    @Override
    public List<CheckInfoListResponse> checkInfoList(CheckInfoListRequest data) {
        return null;
    }

    @Override
    public List<CheckInfoListPoliceResponse> checkInfoListPolice(CheckInfoListPoliceRequest data) {
        return null;
    }

    @Override
    public CheckPersonPhotoResponse checkPersonPhoto(String deviceNo,String IDCard) {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckPersonPhoto");
        builder.queryParam("sfzh", IDCard);
        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);

        CheckPersonPhotoResponse response = new CheckPersonPhotoResponse();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回zp:""
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return response;
        }
        JSONObject o = JSON.parseObject(data);
        List list = JSON.parseArray(o.getString("results"), CheckPersonPhotoResponse.class);
        if(list!=null&&!list.isEmpty()) {
            response = (CheckPersonPhotoResponse) list.get(0);
        //入库
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCardNumber(IDCard);
        checkInfo.setZp(response.getZp());
        int i = checkInfoMapper.updateByPrimaryKeySelective(checkInfo);
        logger.info("照片插入数据库:{}",i);
        }
        return response;
    }

    @Override
    public String checkAddress(String deviceNo, String parentId) {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckAddress");

        if (StringUtils.isEmpty(parentId)) {
            parentId = "";
        } else {
            builder.queryParam("parent", parentId);
        }
        String url = builder.build().toUri().toString();

        return restTemplateForGet(url);

    }

    @Override
    public List<CheckPersonFaceResponse> checkPersonFace(String BASE64img) {
        return null;
    }


    /**
     *   西城公安提供警示信息接口（优先级大于市局接口）
     * @param dataMap
     * @return
     */
    @Override
    public List<CheckPersonJs4XiCheng> checkPersonJs4XiCheng(Map dataMap) {
        String url = "http://10.11.53.207:7979/terminal/request";
        dataMap.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiI4YThiYjU1MDVkNWUwM2E0MDE1ZWI3ZGQ5MmY2MDE2ZCIsImltZWkiOiI4Njk2NjEwMjAzMDk3OTQiLCJpYXQiOjE1ODg4MTQwMjA2MjksIm9yZ2lkIjoiMTEwMTAyNzYwMDAwIiwiYWNjb3VudCI6Ijg4ODgwMyIsInVybCI6IjEwLjExLjUzLjIwNSJ9.Zgkj88ha4Wn3yxM8eSS7_B6aumneqfjAaNLldG5vC3Q");
        dataMap.put("checkType","person");
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter fdt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String checkTime = fdt.format(time);
        dataMap.put("checkTime", checkTime);

        Map subMap = new HashMap();
        subMap.put("params",dataMap);
        subMap.put("method","/qbfx/checkV2/getLocalAlarm");


        Map sendMap = new HashMap();
        sendMap.put("FWQQ_NR",subMap);
        sendMap.put("FWBS","FUN001");



        Map returnMap = new HashMap();
        try {
//            HttpHeaders requestHeaders = new HttpHeaders();
//            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//            requestHeaders.setContentType(type);
//            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
//            HttpEntity<Map> requestEntity = new HttpEntity<Map>(dataMap, requestHeaders);
            logger.info("发送报文：{}", sendMap);
            String data = restTemplate.postForObject(url,sendMap, String.class);
            logger.info("返回报文：{}", data);
            returnMap = JSON.parseObject(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<CheckPersonJs4XiCheng> returnList = new ArrayList<>();
        //查询失败直接返回空list
        if (returnMap == null||returnMap.get("FWTG_NR")==null ) {
            return returnList;
        }
        JSONObject  FWTG_NR  = (JSONObject) returnMap.get("FWTG_NR");
        if(FWTG_NR==null||!FWTG_NR.get("status").equals("成功")){
            return  returnList;
        }
        List<CheckPersonJs4XiCheng> list = JSON.parseArray(FWTG_NR.getString("resultData"),CheckPersonJs4XiCheng.class);

//        List<JSONObject> list = (List<JSONObject>) JSON.parseObject(FWTG_NR, JSONObject.class).get("resultData");
//        for (JSONObject object : list) {
//        }
        return  list;
    }

    @Override
    public Object upLoad(UploadRequest request,String deviceNo) {
        UriComponentsBuilder builder = createBaseUri4Upload(deviceNo, baseUrl + "Upload");
        String url = builder.build().toUri().toString();
        Map returnMap = new HashMap();
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            requestHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
            HttpEntity<String> requestEntity = new HttpEntity<String>(JSON.toJSONString(request), requestHeaders);
            String data = restTemplate.postForEntity(url, requestEntity, String.class).getBody();
            logger.info("总线返回报文：{}", data);
            returnMap = JSON.parseObject(data, Map.class);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        if(returnMap==null||!returnMap.get("status").equals("0")){
              throw new RuntimeException();
        }
//        List list = JSONArray.parseArray((String) returnMap.get("results"),List.class);
//        if(list==null||list.isEmpty()){
//              throw new RuntimeException();
//        }
        return returnMap;
    }

    @Override
    public Object upLoadForeign(UploadRequest request,String deviceNo) {
        return null;
    }


    /**
     * restTemplate get请求封装
     *
     * @param url
     * @return
     */
    private String restTemplateForGet(String url) {
        String returndata = "";
        //请求参数，暂时不用
        //String paramString = "";
        try {
            HttpHeaders requestHeaders = new HttpHeaders();
            MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
            requestHeaders.setContentType(type);
            HttpEntity<String> requestEntity = new HttpEntity<String>(null, requestHeaders);
            ResponseEntity postForEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
            returndata = (String) postForEntity.getBody();
            logger.info("西城接口返回数据：{}",returndata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returndata;
    }

    /**
     * 拼装uri
     */
    private UriComponentsBuilder createBaseUri(String deviceNo, String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        PoliceLoginRecord record = UserUtil.getUserMap().get(deviceNo);
        builder.queryParam("appid", appid)
                .queryParam("deviceid", deviceNo)
                .queryParam("policesfzh", record.getPoliceIDCard())
                .queryParam("policename", record.getPoliceName())
                .queryParam("policeorg", record.getPoliceOrg())
                .queryParam("apptype", appType)
                .queryParam("lon", record.getLon())
                .queryParam("lat", record.getLat());

        return builder;


    }
    private UriComponentsBuilder createBaseUri4Upload(String deviceNo, String url) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        PoliceLoginRecord record = UserUtil.getUserMap().get(deviceNo);
        builder.queryParam("appid", "hxkj_hlz")
                .queryParam("deviceid", deviceNo)
                .queryParam("policesfzh", record.getPoliceIDCard())
                .queryParam("policename", record.getPoliceName())
                .queryParam("policeorg", record.getPoliceOrg())
                .queryParam("apptype", appType)
                .queryParam("lon", record.getLon())
                .queryParam("lat", record.getLat());

        return builder;


    }



}
