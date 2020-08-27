package com.fri.pojo.bo.xicheng.response;

public class CheckForeignPersonBasicReponse {

    //生日 19950414
    private String birthDay;
    // 国家英文 USA
    private String guoJiEn;
    //国籍 840
    private String guoJi;
    //国家中文 美国
    private String guoJiCn;
    //证件号 521021039
    private String identify;
    //国外姓名 GUAN CATHERINE LYNN
    private String foreignerName;
    //性别中文 女
    private String sexCn;
    //性别 2
    private String sex;
    //证件类型 14
    private String cardType;
    //护照类型 普通护照
    private String cardTypeCn;

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getGuoJiEn() {
        return guoJiEn;
    }

    public void setGuoJiEn(String guoJiEn) {
        this.guoJiEn = guoJiEn;
    }

    public String getGuoJi() {
        return guoJi;
    }

    public void setGuoJi(String guoJi) {
        this.guoJi = guoJi;
    }

    public String getGuoJiCn() {
        return guoJiCn;
    }

    public void setGuoJiCn(String guoJiCn) {
        this.guoJiCn = guoJiCn;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getForeignerName() {
        return foreignerName;
    }

    public void setForeignerName(String foreignerName) {
        this.foreignerName = foreignerName;
    }

    public String getSexCn() {
        return sexCn;
    }

    public void setSexCn(String sexCn) {
        this.sexCn = sexCn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardTypeCn() {
        return cardTypeCn;
    }

    public void setCardTypeCn(String cardTypeCn) {
        this.cardTypeCn = cardTypeCn;
    }

    @Override
    public String toString() {
        return "CheckForeignPersonBasicReponse{" +
                "birthDay='" + birthDay + '\'' +
                ", guoJiEn='" + guoJiEn + '\'' +
                ", guoJi='" + guoJi + '\'' +
                ", guoJiCn='" + guoJiCn + '\'' +
                ", identify='" + identify + '\'' +
                ", foreignerName='" + foreignerName + '\'' +
                ", sexCn='" + sexCn + '\'' +
                ", sex='" + sex + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardTypeCn='" + cardTypeCn + '\'' +
                '}';
    }
}

