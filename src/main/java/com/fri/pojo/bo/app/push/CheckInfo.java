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
                '}';
    }
}
