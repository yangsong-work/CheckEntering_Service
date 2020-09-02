package com.fri.service.impl;

import com.fri.common.Location;
import com.fri.dao.*;
import com.fri.exception.NoMessageException;
import com.fri.model.*;
import com.fri.pojo.bo.app.request.*;
import com.fri.pojo.bo.app.response.CheckAddressResponse;
import com.fri.pojo.bo.app.response.DetailsResponse;
import com.fri.pojo.bo.xicheng.request.UploadRequest;
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
    private PoliceLoginRecordMapper policeLoginRecordMapper;
    @Autowired
    private XiChengService xiChengService;
    @Autowired
    private AddressUtil addressUtil;

    @Autowired
    private CheckEnterPushInfoMapper checkEnterPushInfoMapper;
    @Autowired
    private CheckInfoMapper checkInfoMapper;
    @Autowired
    private CheckWarnInfoMapper checkWarnInfoMapper;
    @Autowired
    private CheckAddressMapper checkAddressMapper;
    @Autowired
    private CheckEnterService checkEnterService;
    @Autowired
    private PoliceInfoMapper policeInfoMapper;


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
        List<CheckWarnInfo> list = new ArrayList<>();
        try {
            list = checkWarnInfoMapper.selectByCardNumber(detailRequest.getCardNumber());
            System.out.println("公安3.0返回数据" + list);

        } catch (Exception e) {
            e.printStackTrace();
            log.info("公安3.0警示信息接口请求异常");
        }
        //西城警示信息
        Map jsXiChengMap = new HashMap();
        jsXiChengMap.put("cardId", detailRequest.getCardNumber());
        jsXiChengMap.put("deviceNum", UserUtil.getUserMap().get(detailRequest.getDeviceNo()).getPadId());
        jsXiChengMap.put("policeNumber", UserUtil.getUserMap().get(detailRequest.getDeviceNo()).getPoliceNumber());
        List<CheckPersonJs4XiCheng> personJsList4XiCheng = new ArrayList<>();
        try {
            personJsList4XiCheng = xiChengService.checkPersonJs4XiCheng(jsXiChengMap);
            System.out.println("西城接口返回数据" + list);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("西城分局警示信息接口请求异常");
        }

        List<CheckWarnInfo> list4XiCheng = new ArrayList<>();
        list4XiCheng = transferList(personJsList4XiCheng);
        list4XiCheng = myCompareList(list4XiCheng, "1");

        list = myCompareList(list, "0");

        list4XiCheng.addAll(list);
        Map map = new HashMap();
        DetailsResponse detailsResponse = new DetailsResponse();
        BeanUtils.copyProperties(checkInfo, detailsResponse);
        detailsResponse.setMinzu(checkInfo.getMinzuCn());
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
        map.put("warnList", list4XiCheng);
        return map;
    }

    @Override
    public List<CheckPersonJsDetail2> CheckPersonJsDetail(CheckPersonJsDetailRequest request) throws NoMessageException {
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


    private List myCompareList(List<CheckWarnInfo> list, String isLocal) {
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

    public List<CheckWarnInfo> transferList(List<CheckPersonJs4XiCheng> list) {
        List<CheckWarnInfo> returnList = new ArrayList<>();
        for (CheckPersonJs4XiCheng checkPersonJs4XiCheng : list) {
            CheckWarnInfo checkWarnInfo = new CheckWarnInfo();
            checkWarnInfo.setValue(checkPersonJs4XiCheng.getDATA_CLASS());
            checkWarnInfo.setResourceName(checkPersonJs4XiCheng.getResourceName());

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
            checkWarnInfo.setColor(color);
            returnList.add(checkWarnInfo);
        }
        return returnList;
    }

    @Override
    public Object upLoad(APPUpdateRequest request) {

        //封装西城录入接口
        UploadRequest xichengUploadRequest = new UploadRequest();
        PoliceLoginRecord policeLoginRecord = UserUtil.getUserMap().get(request.getDeviceNo());
        if ("1".equals(request.getCheckObject())) {
            //境内人员
            CheckInfo checkInfo = checkInfoMapper.selectByPrimaryKey(request.getIdentify());
            int age = LocalDateTime.now().getYear() - Integer.valueOf(checkInfo.getCardNumber().substring(6, 10));
            //人员警示信息
            List<CheckWarnInfo> list = new ArrayList<>();
            list = checkWarnInfoMapper.selectByCardNumber(request.getIdentify());

//            LocalDateTime localDateTime =  LocalDateTime.now();
//            DateTimeFormatter dt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//            String time  = localDateTime.format(dt);
            Location location = new Location();
            location.setLat(Double.valueOf(policeLoginRecord.getLat()));
            location.setLon(Double.valueOf(policeLoginRecord.getLon()));
            xichengUploadRequest.setAge(age);
            //TODO 少字段
            xichengUploadRequest.setCardType(checkInfo.getCardType());
            xichengUploadRequest.setCardTypeCn(checkInfo.getCardTypeCn());
            xichengUploadRequest.setCheckObject(request.getCheckObject());
            xichengUploadRequest.setCheckResult(request.getCheckResult());
            xichengUploadRequest.setCheckResult(policeLoginRecord.getCheckTask());
            xichengUploadRequest.setDisposalWay(request.getDisposalWay());
            xichengUploadRequest.setGuoJi(checkInfo.getGuoJi());
            xichengUploadRequest.setGuoJiCn(checkInfo.getGuoJiCn());
            xichengUploadRequest.setHouseHolds(checkInfo.getHouseHolds());
            xichengUploadRequest.setIdentify(checkInfo.getCardNumber());
            xichengUploadRequest.setLocation(location);
            xichengUploadRequest.setMinzu(checkInfo.getMinzu());
            xichengUploadRequest.setMinzuCn(checkInfo.getMinzuCn());
            xichengUploadRequest.setName(checkInfo.getName());
            xichengUploadRequest.setPoliceDeptNo(policeLoginRecord.getPoliceOrg());
            xichengUploadRequest.setPoliceIdCard(policeLoginRecord.getPoliceIDCard());
            xichengUploadRequest.setPoliceNo(policeLoginRecord.getPoliceNumber());
            xichengUploadRequest.setSex(checkInfo.getSex());
            xichengUploadRequest.setSexCn(checkInfo.getSexCn());
            //0 为保存 1为完成  写死为完成
            xichengUploadRequest.setState("1");
            xichengUploadRequest.setTogether(false);
            xichengUploadRequest.setUpdateUser(policeLoginRecord.getPoliceIDCard());
            //警示信息与警示信息简项
            xichengUploadRequest.setWarningInfoDetail(list);
            xichengUploadRequest.setWarningInfoShortHands(list);
            xichengUploadRequest.setXzqh(checkInfo.getXzqh());
            xichengUploadRequest.setXzqhCn(checkInfo.getXzqhCn());




            xiChengService.upLoad(xichengUploadRequest,request.getDeviceNo());
        } else if ("2".equals(request.getCheckObject())) {
            //境外人员
        } else {
            return null;
        }


        return null;
    }


}
