package com.fri.pojo.bo.xicheng.response;

import com.fri.model.CheckPersonJs;

import java.util.List;

public class CheckPersonJsResponse extends BaseResponse {
    private List<CheckPersonJs> checkPersonJsList;

    public List<CheckPersonJs> getCheckPersonJsList() {
        return checkPersonJsList;
    }

    public void setCheckPersonJsList(List<CheckPersonJs> checkPersonJsList) {
        this.checkPersonJsList = checkPersonJsList;
    }

    @Override
    public String toString() {
        return "CheckPersonJsResponse{" +
                "checkPersonJsList=" + checkPersonJsList +
                '}';
    }
}
