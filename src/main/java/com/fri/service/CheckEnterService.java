package com.fri.service;

import com.fri.common.Result;
import com.fri.exception.NoMessageException;
import com.fri.pojo.bo.app.request.CheckPersonJsDetailRequest;
import com.fri.pojo.bo.pinen.VerifyIDCardRequest;
import com.fri.pojo.bo.pinen.VerifyImageRequest;
import com.fri.pojo.bo.pinen.VerifyOcrRequest;

import java.util.Map;

public interface CheckEnterService {
    //核录发送身份证桩请求校验
    Map verifyIDCard(VerifyIDCardRequest verifyIDCardRequest) throws NoMessageException;

    //核录桩发送人脸请求校验
    Map verifyFacePhoto(VerifyImageRequest request) throws NoMessageException;

    Object verifyOcr(VerifyOcrRequest request) throws NoMessageException;

    public void notifyLogin(String deviceNo);


}
