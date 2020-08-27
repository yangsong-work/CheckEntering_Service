package com.fri.model;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public class CheckEnterPushInfoKey {
    private String policeIDCard;

    private LocalDateTime createdTime;

    public String getPoliceIDCard() {
        return policeIDCard;
    }

    public void setPoliceIDCard(String policeIDCard) {
        this.policeIDCard = policeIDCard;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}