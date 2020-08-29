package com.fri.service.impl;

import com.fri.dao.*;
import com.fri.model.*;
import com.fri.pojo.bo.app.request.*;
import com.fri.pojo.bo.app.response.CheckAddressResponse;
import com.fri.pojo.bo.app.response.DetailsResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonJs4XiCheng;
import com.fri.service.APPService;
import com.fri.service.CheckEnterService;
import com.fri.service.XiChengService;
import com.fri.utils.AddressUtil;
import com.fri.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class APPServiceImpl implements APPService {
    public static Logger log = LoggerFactory.getLogger(APPService.class);
    @Autowired
    PoliceLoginRecordMapper policeLoginRecordMapper;
    @Autowired
    XiChengService xiChengService;
    @Autowired
    AddressUtil addressUtil;

    @Autowired
    CheckEnterPushInfoMapper checkEnterPushInfoMapper;
    @Autowired
    CheckInfoMapper checkInfoMapper;
    @Autowired
    CheckWarnInfoMapper checkWarnInfoMapper;
    @Autowired
    CheckAddressMapper checkAddressMapper;
    @Autowired
    CheckEnterService checkEnterService;
    @Autowired
    PoliceInfoMapper policeInfoMapper;


    @Override
    public Integer login(LoginRequest loginRequest) {
        PoliceLoginRecord record = new PoliceLoginRecord();
        BeanUtils.copyProperties(loginRequest, record);
        record.setCreatedTime(LocalDateTime.now());
        record.setUpdatedTime(record.getCreatedTime());
        //  PoliceInfo policeInfo = policeInfoMapper.selectName(loginRequest.getPoliceIDCard());
        // record.setPoliceOrg(policeInfo.getOrgCode());
        //删除遗留数据
        if (UserUtil.getUserMap().containsKey(loginRequest.getDeviceNo())) {
            UserUtil.getUserMap().remove(loginRequest.getDeviceNo());
        }
        if (UserUtil.getPadPushMap().containsKey(loginRequest.getPadId())) {
            UserUtil.getPadPushMap().remove(loginRequest.getPadId());
        }

        UserUtil.getUserMap().put(loginRequest.getDeviceNo(), record);
        //创建推送记录表信息
        CheckEnterPushInfo pushInfo = new CheckEnterPushInfo();
        pushInfo.setPadId(loginRequest.getPadId());
        pushInfo.setPoliceIDCard(loginRequest.getPoliceIDCard());
        pushInfo.setCreatedTime(LocalDateTime.now());
        checkEnterPushInfoMapper.insertSelective(pushInfo);
        UserUtil.getPadPushMap().put(loginRequest.getPadId(), pushInfo);

        int i = policeLoginRecordMapper.insert(record);


        return i;
    }

    @Override
    public int checkOption(CheckOptionRequest checkOptionRequest) {
        UserUtil.getUserMap().get(checkOptionRequest.getDeviceNo()).setCheckAddress(checkOptionRequest.getCheckAddress());
        // 通知核录桩
        //TODO 测试代码 上线删除
        //
        checkEnterService.notifyLogin(checkOptionRequest.getPadId());
        return policeLoginRecordMapper.updateCheckOption(checkOptionRequest);
    }

    @Override
    public Integer logout(LogoutRequest logoutRequest) {
        //登出删除维护的登录map
        UserUtil.getUserMap().remove(logoutRequest.getDeviceNo());
        //移除socket连接
        //TODO 通知核录桩
        PoliceLoginRecord record = new PoliceLoginRecord();
        record.setDeviceNo(logoutRequest.getDeviceNo());
        record.setPadId(logoutRequest.getPadId());
        record.setLogoutTime(LocalDateTime.now());
        record.setUpdatedTime(record.getLogoutTime());
        return policeLoginRecordMapper.updateByPadIdDeviceNo(record);
    }


    @Override
    public List getCheckAddress(String deviceNo, String parentId) {
        List<CheckAddress> checkAddressList = addressUtil.checkAddress(deviceNo, parentId);
        List<CheckAddressResponse> returnList = new ArrayList<>();
        for (CheckAddress checkAddress : checkAddressList) {
            CheckAddressResponse res = new CheckAddressResponse();
            res.setId(checkAddress.getId());
            res.setValue(checkAddress.getValue());
            res.setParentId(checkAddress.getParentId() == null ? "" : checkAddress.getParentId());
            res.setLatitude(checkAddress.getLatitude() == null ? "" : checkAddress.getLatitude());
            res.setLongitude(checkAddress.getLongitude() == null ? "" : checkAddress.getLongitude());
            returnList.add(res);
        }
        return returnList;
    }

    @Override
    public Map getDetails(DetailRequest detailRequest) {
        //人员基本信息
        CheckInfo checkInfo = checkInfoMapper.selectByPrimaryKey(detailRequest.getCardNumber());
        //人员警示信息排序
        List<CheckWarnInfo> list = checkWarnInfoMapper.selectByCardNumber(detailRequest.getCardNumber());
        //西城警示信息
        Map jsXiChengMap = new HashMap();
        jsXiChengMap.put("cardId", detailRequest.getCardNumber());
        jsXiChengMap.put("deviceNum", UserUtil.getUserMap().get(detailRequest.getDeviceNo()).getPadId());
        jsXiChengMap.put("policeNumber", UserUtil.getUserMap().get(detailRequest.getDeviceNo()).getPoliceNumber());
        List<CheckPersonJs4XiCheng> personJsList4XiCheng = xiChengService.checkPersonJs4XiCheng(jsXiChengMap);
        List<CheckWarnInfo> list4XiCheng = new ArrayList<>();
        list4XiCheng = transferList(personJsList4XiCheng);
        list4XiCheng = myCompareList(list4XiCheng,"1");

        list = myCompareList(list,"0");

        list.addAll(list4XiCheng);
        Map map = new HashMap();
        DetailsResponse detailsResponse = new DetailsResponse();
        BeanUtils.copyProperties(checkInfo, detailsResponse);
        //封装response信息
        int age = LocalDateTime.now().getYear() - Integer.valueOf(checkInfo.getCardNumber().substring(6, 10));
        String checkAddressId = "";
        try {
            checkAddressId = UserUtil.getUserMap().get(detailRequest.getDeviceNo()).getCheckAddress();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("此核录桩未绑定:{}", detailRequest.getDeviceNo());
        }
        //以防没有数据
        detailsResponse.setCheckArea("");
        if (checkAddressId != null) {
            CheckAddress checkAddress = checkAddressMapper.selectByPrimaryKey(checkAddressId);
            detailsResponse.setCheckArea(checkAddress.getValue());
        }
        detailsResponse.setAge(age + "");
        detailsResponse.setCheckChannel(detailRequest.getDeviceNo());
        detailsResponse.setZp(checkInfo.getZp() == null ? "" : checkInfo.getZp());
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
        String time = localDateTime.format(dt);
        detailsResponse.setWarnTime(time);

        map.put("detailsResponse", detailsResponse);
        map.put("warnList", list);
        return map;
    }

    @Override
    public List<CheckPersonJsDetail2> CheckPersonJsDetail(CheckPersonJsDetailRequest request) {
        //请求    拿信息
        List<CheckPersonJsDetail2> checkPersonJsDetail2s = xiChengService.checkPersonJsDetail(request);
        if (checkPersonJsDetail2s == null) {
            return null;
        }
        // Map returnMap = new HashMap();
        //  returnMap.put("data",checkPersonJsDetail2s);

        return checkPersonJsDetail2s;
    }

    @Override
    public List<CheckPersonJsDetail2> CheckLocalJsDetail(CheckPersonJsDetailRequest request) {
        List<CheckPersonJsDetail2> checkPersonJsDetail2s = xiChengService.checkLocalJsDetail(request);
        if (checkPersonJsDetail2s == null) {
            return null;
        }
        // Map returnMap = new HashMap();
        //returnMap.put("data",checkPersonJsDetail2s);
        return checkPersonJsDetail2s;
    }

    @Override
    public PoliceManRequest getPoliceMessage(String userAccount) {
        PoliceInfo policeInfo = policeInfoMapper.selectByAccount(userAccount);
        PoliceManRequest policeManRequest = new PoliceManRequest();
        if (policeInfo != null) {
            policeManRequest.setOrgCode(policeInfo.getOrgCode());
            policeManRequest.setUserAccount(policeInfo.getUserAccount());
            policeManRequest.setUserIdCard(policeInfo.getUserIdCard());
            policeManRequest.setUserName(policeInfo.getUserName());
        }
        return policeManRequest;
    }


    private List myCompareList(List<CheckWarnInfo> list,String isLocal) {
        List<CheckWarnInfo> returnList = new ArrayList<>();
        //按红黄绿paixv
        List<CheckWarnInfo> yellowList = new ArrayList<>();
        List<CheckWarnInfo> greenList = new ArrayList<>();
        for (CheckWarnInfo checkWarnInfo : list) {
            checkWarnInfo.setIsLocal(isLocal);
            if (checkWarnInfo.getColor().equals("red")) {
                returnList.add(checkWarnInfo);
            }
            if (checkWarnInfo.getColor().equals("yellow")) {
                yellowList.add(checkWarnInfo);
            }
            if (checkWarnInfo.getColor().equals("green")) {
                greenList.add(checkWarnInfo);
            }
        }
        returnList.addAll(yellowList);
        returnList.addAll(greenList);
        return returnList;
    }

    private List<CheckWarnInfo> transferList(List<CheckPersonJs4XiCheng> list) {
        List<CheckWarnInfo> returnList = new ArrayList<>();
        for (CheckPersonJs4XiCheng checkPersonJs4XiCheng : list) {
            CheckPersonJs checkPersonJs = new CheckPersonJs();
            checkPersonJs.setValue(checkPersonJs4XiCheng.getDATA_CLASS());
            checkPersonJs.setResourceName(checkPersonJs4XiCheng.getResourceName());

            String color = "white";
            switch (checkPersonJs4XiCheng.getALARM_LEVEL()) {
                case "1":
                    color = "green";
                    break;
                case "2":
                    color = "yellow";
                    break;
                case "3":
                    color = "red";
                    break;
            }
            checkPersonJs.setColor(color);
        }
        return  returnList;
    }


}
