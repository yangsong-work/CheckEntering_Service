package com.fri.model;

public class CheckPersonJs {
    private String key;
    private String color;
    private String order;
    private String value;
    private String disposalWay;
    private String resourceName;

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    @Override
    public String toString() {
        return "CheckPersonJs{" +
                "key='" + key + '\'' +
                ", color='" + color + '\'' +
                ", order='" + order + '\'' +
                ", value='" + value + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", resourceName='" + resourceName + '\'' +
                '}';
    }
}
