package com.fri.pojo.bo.xicheng.request;

public class CheckInfoHistoryResquest {
    //类别 1 中国人，2外国人，3车辆
    private String obj;
    //国籍（lb=2时必填）
    private String gj;
    //证件类型（lb=2,3时必填，填号牌种类或外国人证件类别）
    private String lb;
    //号码（填身份证号，护照号，车牌号）
    private String hm;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb;
    }

    public String getHm() {
        return hm;
    }

    public void setHm(String hm) {
        this.hm = hm;
    }

    @Override
    public String toString() {
        return "CheckInfoHistoryResquestVO{" +
                "obj='" + obj + '\'' +
                ", gj='" + gj + '\'' +
                ", lb='" + lb + '\'' +
                ", hm='" + hm + '\'' +
                '}';
    }
}
