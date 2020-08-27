package com.fri.model;

import java.time.LocalDateTime;

public class PoliceLoginRecord {
    private Long id;

    private String policeName;

    private String policeNumber;

    private String policeIDCard;

    private String policeOrg;

    private String padId;

    private String deviceNo;

    private String deviceScore;

    private String lon;

    private String lat;

    private String checkAddress;

    private String checkReason;

    private String checkTask;

    private String verifyscore;

    private LocalDateTime loginTime;

    private LocalDateTime logoutTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPoliceNumber() {
        return policeNumber;
    }

    public void setPoliceNumber(String policeNumber) {
        this.policeNumber = policeNumber;
    }

    public String getPoliceIDCard() {
        return policeIDCard;
    }

    public void setPoliceIDCard(String policeIDCard) {
        this.policeIDCard = policeIDCard;
    }

    public String getPoliceOrg() {
        return policeOrg;
    }

    public void setPoliceOrg(String policeOrg) {
        this.policeOrg = policeOrg;
    }

    public String getPadId() {
        return padId;
    }

    public void setPadId(String padId) {
        this.padId = padId;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceScore() {
        return deviceScore;
    }

    public void setDeviceScore(String deviceScore) {
        this.deviceScore = deviceScore;
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

    public String getCheckAddress() {
        return checkAddress;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public String getCheckReason() {
        return checkReason;
    }

    public void setCheckReason(String checkReason) {
        this.checkReason = checkReason;
    }

    public String getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(String checkTask) {
        this.checkTask = checkTask;
    }

    public String getVerifyscore() {
        return verifyscore;
    }

    public void setVerifyscore(String verifyscore) {
        this.verifyscore = verifyscore;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public LocalDateTime getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(LocalDateTime logoutTime) {
        this.logoutTime = logoutTime;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }
}