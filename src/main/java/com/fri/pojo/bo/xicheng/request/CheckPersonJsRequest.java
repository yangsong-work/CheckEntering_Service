package com.fri.pojo.bo.xicheng.request;

public class CheckPersonJsRequest {
    private String IDCard;

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    @Override
    public String toString() {
        return "checkPersonJsRequest{" +
                "IDCard='" + IDCard + '\'' +
                '}';
    }
}
