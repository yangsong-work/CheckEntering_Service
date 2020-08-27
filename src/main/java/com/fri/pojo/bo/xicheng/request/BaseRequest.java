package com.fri.pojo.bo.xicheng.request;

public class BaseRequest {
    //应用（授权码）
    private String appid;
    //设备号，除PC外，其他设备必填
    private String deviceNo;
    //责任民警身份证号
    private String policesfzh;
    //责任民警姓名（汉字）
    private String policename;
    //责任民警单位（汉字）
    private String policeorg;
    //类型 pcd（PC端应用），app（移动警务应用），hlz（核录桩）
    private String apptype;
    //经度坐标，除PC核录外，其他设备必填
    private String lon;
    //维度坐标，除PC核录外，其他设备必填
    private String lat;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getPolicesfzh() {
        return policesfzh;
    }

    public void setPolicesfzh(String policesfzh) {
        this.policesfzh = policesfzh;
    }

    public String getPolicename() {
        return policename;
    }

    public void setPolicename(String policename) {
        this.policename = policename;
    }

    public String getPoliceorg() {
        return policeorg;
    }

    public void setPoliceorg(String policeorg) {
        this.policeorg = policeorg;
    }

    public String getApptype() {
        return apptype;
    }

    public void setApptype(String apptype) {
        this.apptype = apptype;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "appid='" + appid + '\'' +
                ", deviceNo='" + deviceNo + '\'' +
                ", policesfzh='" + policesfzh + '\'' +
                ", policename='" + policename + '\'' +
                ", policeorg='" + policeorg + '\'' +
                ", apptype='" + apptype + '\'' +
                ", lon='" + lon + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}
