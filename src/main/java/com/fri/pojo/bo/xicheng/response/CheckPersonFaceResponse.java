package com.fri.pojo.bo.xicheng.response;

public class CheckPersonFaceResponse extends BaseResponse {
    //相似度
    private String similaritydegree;
    //身份证号
    private String idnumber;

    public String getSimilaritydegree() {
        return similaritydegree;
    }

    public void setSimilaritydegree(String similaritydegree) {
        this.similaritydegree = similaritydegree;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    @Override
    public String toString() {
        return "CheckPersonFaceResponse{" +
                "similaritydegree='" + similaritydegree + '\'' +
                ", idnumber='" + idnumber + '\'' +
                '}';
    }
}
