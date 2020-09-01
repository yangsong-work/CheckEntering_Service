package com.fri.pojo.bo.app.push;

public class CheckInfo {
    private Integer checkNumber;
    private Integer warningNumber;
    private Integer redWarningNumber;
    private Integer yellowWarningNumber;
    private Integer greenWarningNumber;
    private Integer verifyNumber;
    private String warningColor;
    private String img;
    private String cardNumber;
    //2020.09.01新增需求保留核查人数，增添基本信息
    private String name;
    private String sex;
    private String age;
    private String minzuCn;
    private String  houseHolds;

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMinzuCn() {
        return minzuCn;
    }

    public void setMinzuCn(String minzuCn) {
        this.minzuCn = minzuCn;
    }

    public String getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(String houseHolds) {
        this.houseHolds = houseHolds;
    }

    public Integer getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(Integer checkNumber) {
        this.checkNumber = checkNumber;
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

    public Integer getVerifyNumber() {
        return verifyNumber;
    }

    public void setVerifyNumber(Integer verifyNumber) {
        this.verifyNumber = verifyNumber;
    }

    public String getWarningColor() {
        return warningColor;
    }

    public void setWarningColor(String warningColor) {
        this.warningColor = warningColor;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "CheckInfo{" +
                "checkNumber=" + checkNumber +
                ", warningNumber=" + warningNumber +
                ", redWarningNumber=" + redWarningNumber +
                ", yellowWarningNumber=" + yellowWarningNumber +
                ", greenWarningNumber=" + greenWarningNumber +
                ", verifyNumber=" + verifyNumber +
                ", warningColor='" + warningColor + '\'' +
                ", img='" + img + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", minzuCn='" + minzuCn + '\'' +
                ", houseHolds='" + houseHolds + '\'' +
                '}';
    }
}
