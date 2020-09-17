package com.fri.model;

public class EnterInfoWithBLOBs extends EnterInfo {
    private String personInfoJson;

    private String foreignerBaseInfoJson;

    public String getPersonInfoJson() {
        return personInfoJson;
    }

    public void setPersonInfoJson(String personInfoJson) {
        this.personInfoJson = personInfoJson;
    }

    public String getForeignerBaseInfoJson() {
        return foreignerBaseInfoJson;
    }

    public void setForeignerBaseInfoJson(String foreignerBaseInfoJson) {
        this.foreignerBaseInfoJson = foreignerBaseInfoJson;
    }
}