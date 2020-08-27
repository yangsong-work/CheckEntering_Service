package com.fri.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fri.dao.CheckInfoForeignMapper;
import com.fri.dao.CheckInfoMapper;
import com.fri.dao.CheckWarnInfoMapper;
import com.fri.dao.Test1Mapper;
import com.fri.model.*;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public CheckPersonBasicInfoResponse checkPersonBasicInfo(String IDCard, String deviceNo) {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckPersonBasicInfo");
        builder.queryParam("sfzh", IDCard);
        String url = builder.build().toUri().toString();
        String result = restTemplateForGet(url);
//        //TODO 测试代码
//        String result = test1Mapper.select1("5").getTtt();
        JSONObject o = JSON.parseObject(result);
        CheckPersonBasicInfoResponse checkPersonBasicInfoResponse = JSON.parseArray(o.getString("results"), CheckPersonBasicInfoResponse.class).get(0);
        CheckInfo checkInfo = new CheckInfo();
        BeanUtils.copyProperties(checkPersonBasicInfoResponse,checkInfo);
        checkInfoMapper.insertUser(checkInfo);
        return checkPersonBasicInfoResponse;
    }


    @Override
    public List<CheckPersonJs> checkPersonJs(String IDCard, String deviceNo) {
        UriComponentsBuilder builder = createBaseUri(deviceNo, baseUrl + "CheckPersonJs");
        builder.queryParam("sfzh", IDCard);
        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);
//        //TODO 测试代码
//
//        String  data = test1Mapper.select1("4").getTtt();

        List<CheckPersonJs> returnList = new ArrayList<>();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回空list
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return returnList;
        }
        JSONObject o = JSON.parseObject(data);
        returnList = JSON.parseArray(o.getString("results"), CheckPersonJs.class);

        //先删除再插入
        checkWarnInfoMapper.deleteByCardNumber(IDCard);
        List<CheckWarnInfo> list = new ArrayList<>();
        if(returnList!=null&&!returnList.isEmpty()){
        for(CheckPersonJs checkPersonJs:returnList){
        CheckWarnInfo checkWarnInfo = new CheckWarnInfo();
             BeanUtils.copyProperties(checkPersonJs,checkWarnInfo);
             checkWarnInfo.setCardNumber(IDCard);
             list.add(checkWarnInfo);
        }
        checkWarnInfoMapper.insertBatch(list);
        }
        return returnList;
    }

    @Override
    public String checkPersonJsDetail(String IDCard) {

        return null;
    }

    @Override
    public CheckForeignPersonBasicReponse checkForeignPersonBasicInfo(CheckForeignPersonInfoRequest request) {
        UriComponentsBuilder builder = createBaseUri(request.getDeviceNo(), baseUrl + "CheckPersonJs");
        builder.queryParam("gj", request.getGj());
        builder.queryParam("zjlb", request.getZjlb());
        builder.queryParam("zjhm", request.getZjhm());

        String url = builder.build().toUri().toString();
        String data = restTemplateForGet(url);

//        //TODO 测试代码
//
//        String  data = test1Mapper.select1("6").getTtt();

        JSONObject o = JSON.parseObject(data);
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

//        //TODO 测试代码
//        String  data = test1Mapper.select1("7").getTtt();

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
//        //TODO 测试代码
//
//        String  data = test1Mapper.select1("8").getTtt();

        CheckPersonPhotoResponse response = new CheckPersonPhotoResponse();
        Map<String, Object> map = JSON.parseObject(data, Map.class);
        //查询失败直接返回zp:""
        if (map == null || !(map.get("status").toString().equals("0"))) {
            return response;
        }
        JSONObject o = JSON.parseObject(data);
        response = JSON.parseArray(o.getString("results"), CheckPersonPhotoResponse.class).get(0);

        //入库
        CheckInfo checkInfo = new CheckInfo();
        checkInfo.setCardNumber(IDCard);
        checkInfo.setZp(response.getZp());
        int i = checkInfoMapper.updateByPrimaryKeySelective(checkInfo);
        System.out.println(i);
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
            HttpEntity<String> requestEntity = new HttpEntity<String>("", requestHeaders);
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


}
