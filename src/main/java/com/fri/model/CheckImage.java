package com.fri.model;

public class CheckImage {
    private Integer id;

    private String cardNo;

    private String img;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "CheckImage{" +
                "id=" + id +
                ", cardNo='" + cardNo + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}