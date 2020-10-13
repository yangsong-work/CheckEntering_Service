package com.fri.pojo.bo.pinen;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class VerifyIDCardRequest {

    //设备号
    @NotBlank(message = "设备号不能为空")
    private String deviceNo;

    //抓拍图片base64
    @NotBlank(message = "图片不能为空")
    private String pCaptureImage;

    //是否为重传数据，0实时，1为重传
    @NotNull(message = "重传数据不能为空")
    private Integer isReupload;

    //照片比对值
    //@NotBlank(message = "照片比对值不能为空")
    private String compareValue;

    //核录方式：0为刷证核录，1为手工核录
    @NotNull(message = "核录方式不能为空")
    private Integer checkFrom;

    //证件号
    @NotBlank(message = "证件号不能为空")
    private String pCardNo;

    @NotNull(message = "人工核验状态不能为空")
    private Integer compareStatus;

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getpCaptureImage() {
        return pCaptureImage;
    }

    public void setpCaptureImage(String pCaptureImage) {
        this.pCaptureImage = pCaptureImage;
    }

    public Integer getIsReupload() {
        return isReupload;
    }

    public void setIsReupload(Integer isReupload) {
        this.isReupload = isReupload;
    }

    public String getCompareValue() {
        return compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }

    public Integer getCheckFrom() {
        return checkFrom;
    }

    public void setCheckFrom(Integer checkFrom) {
        this.checkFrom = checkFrom;
    }

    public String getpCardNo() {
        return pCardNo;
    }

    public void setpCardNo(String pCardNo) {
        this.pCardNo = pCardNo;
    }

    public Integer getCompareStatus() {
        return compareStatus;
    }

    public void setCompareStatus(Integer compareStatus) {
        this.compareStatus = compareStatus;
    }

    @Override
    public String toString() {
        return "VerifyIDCardRequest{" +
                "deviceNo='" + deviceNo + '\'' +
                ", pCaptureImage='" + pCaptureImage + '\'' +
                ", isReupload=" + isReupload +
                ", compareValue='" + compareValue + '\'' +
                ", checkFrom=" + checkFrom +
                ", compareStatus=" + compareStatus +
                '}';
    }
}
