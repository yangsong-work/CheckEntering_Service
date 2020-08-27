package com.fri.pojo.bo.xicheng.response;

public class CheckPersonPhotoResponse  {
    private String zp;

    public CheckPersonPhotoResponse(){
        this.zp="";
    }
    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }

    @Override
    public String toString() {
        return "CheckPersonPhotoResponse{" +
                "zp='" + zp + '\'' +
                '}';
    }
}
