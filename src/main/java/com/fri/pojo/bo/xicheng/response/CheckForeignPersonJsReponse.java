package com.fri.pojo.bo.xicheng.response;

public class CheckForeignPersonJsReponse {
    //警示信息
    private String value;
    //
    private String disposalWay;
    //警示颜色 yellow
    private String color;
    //
    private String resourceName;
    //
    private String order;
    //
    private String key;

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

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "CheckForeignPersonJsReponse{" +
                "value='" + value + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", color='" + color + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", order='" + order + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
