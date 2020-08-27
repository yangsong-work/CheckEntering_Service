package com.fri.pojo.bo.xicheng.response;

import com.fri.model.CheckAddress;

import java.util.List;

public class CheckAddressResponse extends BaseResponse{
    private List<CheckAddress> results;

    public List<CheckAddress> getResult() {
        return results;
    }

    public void setResult(List<CheckAddress> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "CheckAddressResponse{" +
                "results=" + results +
                '}';
    }
}
