package com.fri.pojo.bo.xicheng.response;

public class SsoResponse {
    private String OrgNo;
    private String deptNo;
    private String deptName;
    private String suborgNo;
    private String name;
    private String policeNo;
    private String username;
    private String orgName;
    private String subOrgName;
    private String key;

    public String getOrgNo() {
        return OrgNo;
    }

    public void setOrgNo(String orgNo) {
        OrgNo = orgNo;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getSuborgNo() {
        return suborgNo;
    }

    public void setSuborgNo(String suborgNo) {
        this.suborgNo = suborgNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSubOrgName() {
        return subOrgName;
    }

    public void setSubOrgName(String subOrgName) {
        this.subOrgName = subOrgName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "SsoResponse{" +
                "OrgNo='" + OrgNo + '\'' +
                ", deptNo='" + deptNo + '\'' +
                ", deptName='" + deptName + '\'' +
                ", suborgNo='" + suborgNo + '\'' +
                ", name='" + name + '\'' +
                ", policeNo='" + policeNo + '\'' +
                ", username='" + username + '\'' +
                ", orgName='" + orgName + '\'' +
                ", subOrgName='" + subOrgName + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
