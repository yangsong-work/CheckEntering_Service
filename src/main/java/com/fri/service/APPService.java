package com.fri.service;

import com.fri.exception.NoMessageException;
import com.fri.model.CheckAddress;
import com.fri.model.CheckPersonJsDetail2;
import com.fri.model.CheckWarnInfo;
import com.fri.model.PeopleCountInfo;
import com.fri.pojo.bo.app.request.*;
import com.fri.pojo.bo.xicheng.response.CheckPersonBasicInfoResponse;
import com.fri.pojo.bo.xicheng.response.CheckPersonJs4XiCheng;

import java.util.List;
import java.util.Map;

public interface APPService {

    Integer login(LoginRequest loginRequest);

    int checkOption(CheckOptionRequest checkOptionRequest);

    Integer logout(LogoutRequest logoutRequest);

    List getCheckAddress(String deviceNo, String parentId);

    Map getDetails(DetailRequest detailRequest);

    PoliceManRequest getPoliceMessage(String userAccount);

    List<CheckPersonJsDetail2> CheckPersonJsDetail(CheckPersonJsDetailRequest request) throws NoMessageException;

    List<CheckPersonJsDetail2> CheckLocalJsDetail(CheckPersonJsDetailRequest request);

    List<CheckWarnInfo> transferList(List<CheckPersonJs4XiCheng> list);
    PeopleCountInfo getCountStatistics(CountRequest request);

    List<CheckPersonBasicInfoResponse> getPeopleBasicMessage(PeopleBasicMessageRequest request);

    Boolean upLoad(APPUpdateRequest request);
}
