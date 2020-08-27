package com.fri.pojo.bo.xicheng.request;

public class CheckForeignPersonInfoRequest {
    private String deviceNo;
    //国籍
    private String Gj;
    //证件类型
    private String Zjlb;
    //证件号码
    private String Zjhm;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getGj() {
        return Gj;
    }

    public void setGj(String gj) {
        Gj = gj;
    }

    public String getZjlb() {
        return Zjlb;
    }

    public void setZjlb(String zjlb) {
        Zjlb = zjlb;
    }

    public String getZjhm() {
        return Zjhm;
    }

    public void setZjhm(String zjhm) {
        Zjhm = zjhm;
    }

    @Override
    public String toString() {
        return "CheckForeignPersonInfoRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", Gj='" + Gj + '\'' +
                ", Zjlb='" + Zjlb + '\'' +
                ", Zjhm='" + Zjhm + '\'' +
                '}';
    }
}
