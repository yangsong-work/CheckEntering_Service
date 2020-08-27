package com.fri.pojo.bo.pinen;

import org.hibernate.validator.constraints.NotBlank;

public class VerifyImageRequest {
    @NotBlank(message = "核录桩设备号不能为空")
    private String deviceNo;
    @NotBlank(message = "BASE64格式图像不能为空")
    private String img;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "VerifyImageRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
