package com.fri.service;

import com.alibaba.fastjson.JSONObject;
import com.fri.model.CheckAddress;
import com.fri.model.CheckPersonJs;
import com.fri.pojo.bo.xicheng.request.CheckForeignPersonInfoRequest;
import com.fri.pojo.bo.xicheng.request.CheckInfoHistoryResquest;
import com.fri.pojo.bo.xicheng.request.CheckInfoListPoliceRequest;
import com.fri.pojo.bo.xicheng.request.CheckInfoListRequest;
import com.fri.pojo.bo.xicheng.response.*;

import java.util.List;

public interface XiChengService {
    //2.请求核查对象历史统计信息
    List<CheckInfoHistoryResponse> checkInfoHistory(CheckInfoHistoryResquest data);

    //5.请求人的基本信息
    CheckPersonBasicInfoResponse checkPersonBasicInfo(String IDCard, String deviceNo);

    //6.请求人的警示信息
    List<CheckPersonJs> checkPersonJs(String IDCard, String deviceNo);

    //7.请求人的警示详细信息
    String checkPersonJsDetail(String IDCard);

    //9.请求境外人员基本信息(ocr)
    CheckForeignPersonBasicReponse checkForeignPersonBasicInfo(CheckForeignPersonInfoRequest request);

    //10.请求境外人员的警示信息(ocr)
    List<CheckForeignPersonJsReponse> checkForeignPersonJsInfo(CheckForeignPersonInfoRequest request);

    //12.请求核查对象轨迹信息
    List<CheckInfoListResponse> checkInfoList(CheckInfoListRequest data);

    //13.请求民警核查记录
    List<CheckInfoListPoliceResponse> checkInfoListPolice(CheckInfoListPoliceRequest data);

    //15.请求境内人员照片信息
    CheckPersonPhotoResponse checkPersonPhoto(String deviceNo,String IDCard);

    //18.请求核查地点接口
    String checkAddress(String deviceNo, String parentId);

    //25.人脸识别接口
    List<CheckPersonFaceResponse> checkPersonFace(String BASE64img);


}
