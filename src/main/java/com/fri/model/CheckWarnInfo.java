package com.fri.model;

public class CheckWarnInfo {
    private Long id;

    private String cardNumber;

    private String key;

    private String order;

    private String value;

    private String disposalWay;

    private String color;

    private String resourceName;

    private String isLocal;

    public String getIsLocal() {
        return isLocal;
    }

    public void setIsLocal(String isLocal) {
        this.isLocal = isLocal;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDisposalWay() {
        return disposalWay;
    }

    public void setDisposalWay(String disposalWay) {
        this.disposalWay = disposalWay;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String toString() {
        return "CheckWarnInfo{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", key='" + key + '\'' +
                ", order='" + order + '\'' +
                ", value='" + value + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", color='" + color + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", isLocal='" + isLocal + '\'' +
                '}';
    }
}