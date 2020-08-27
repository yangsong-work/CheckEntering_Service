package com.fri.model;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class CheckEnterPushInfo extends CheckEnterPushInfoKey {
    private String padId;

    private Integer checkNumber;

    private Integer verifyNumber;

    private Integer warningNumber;

    private Integer redWarningNumber;

    private Integer yellowWarningNumber;

    private Integer greenWarningNumber;

    private LocalDateTime updatedTime;

    public CheckEnterPushInfo() {
        this.checkNumber = 0;
        this.verifyNumber = 0;
        this.warningNumber = 0;
        this.redWarningNumber = 0;
        this.yellowWarningNumber = 0;
        this.greenWarningNumber = 0;
    }

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Integer getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(Integer verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public Integer getWarningNumber() {
        return warningNumber;
    }

    public void setWarningNumber(Integer warningNumber) {
        this.warningNumber = warningNumber;
    }

    public Integer getRedWarningNumber() {
        return redWarningNumber;
    }

    public void setRedWarningNumber(Integer redWarningNumber) {
        this.redWarningNumber = redWarningNumber;
    }

    public Integer getYellowWarningNumber() {
        return yellowWarningNumber;
    }

    public void setYellowWarningNumber(Integer yellowWarningNumber) {
        this.yellowWarningNumber = yellowWarningNumber;
    }

    public Integer getGreenWarningNumber() {
        return greenWarningNumber;
    }

    public void setGreenWarningNumber(Integer greenWarningNumber) {
        this.greenWarningNumber = greenWarningNumber;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    @Override
    public String toString() {
        return "CheckEnterPushInfo{" +
                "padId='" + padId + '\'' +
                ", checkNumber=" + checkNumber +
                ", verifyNumber=" + verifyNumber +
                ", warningNumber=" + warningNumber +
                ", redWarningNumber=" + redWarningNumber +
                ", yellowWarningNumber=" + yellowWarningNumber +
                ", greenWarningNumber=" + greenWarningNumber +
                ", updatedTime=" + updatedTime +
                '}';
    }
}