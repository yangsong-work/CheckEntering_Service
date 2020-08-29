package com.fri.service;

import com.fri.model.CheckAddress;
import com.fri.model.CheckPersonJsDetail2;
import com.fri.pojo.bo.app.request.*;
import com.fri.pojo.bo.app.response.CheckAddressResponse;
import com.fri.pojo.bo.app.response.DetailsResponse;

import java.util.List;
import java.util.Map;

public interface APPService {

    Integer login(LoginRequest loginRequest);

    int checkOption(CheckOptionRequest checkOptionRequest);

    Integer logout(LogoutRequest logoutRequest);

    List getCheckAddress(String deviceNo,String parentId);

    Map getDetails(DetailRequest detailRequest);

    PoliceManRequest getPoliceMessage(String userAccount);
    List<CheckPersonJsDetail2> CheckPersonJsDetail(CheckPersonJsDetailRequest request);
    List<CheckPersonJsDetail2> CheckLocalJsDetail(CheckPersonJsDetailRequest request);
}
