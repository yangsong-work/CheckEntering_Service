package com.fri.model;

public class PeopleBasicInfo {
    private String name;
    private String sex;
    //String age;
    private String houseHolds;
    private String minzu;
    private String idCard;
    private String zp;
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(String houseHolds) {
        this.houseHolds = houseHolds;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getZp() {
        return zp;
    }

    public void setZp(String zp) {
        this.zp = zp;
    }
}
