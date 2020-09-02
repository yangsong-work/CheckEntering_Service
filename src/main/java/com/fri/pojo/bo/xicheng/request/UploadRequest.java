package com.fri.pojo.bo.xicheng.request;

import com.fri.common.Location;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class UploadRequest {
    //警员身份证号
     private String policeIdCard;
     private String policeDeptNo;
     private String policeName;
     private String policeNo;
     //核查人物
    private String checkTask;
    //false
    private Boolean together;
    private String  checkObject;
    //被核查人身份证号
    private String  identify;
    //处置方式
    private String disposalWay;
    //核查结论
    private String checkResult;
    //格式
    //"location":{
    //        "lat":40.7339602369142,
    //                "lon":116.634220039384
    //    }
    private Location location;
    //
    private String locationNameLevelOne;
    private String locationNameLevelTwo;
    private String locationNameReal;
    private String locationName;
    private String checkSourceType;
    private String state;
    //被核查人姓名
    private String name;
    //户籍地址
    private String houseHolds;
    //性别代码
    private String sex;
    //行政区划代码
    private String xzqh;
    //民族代码
    private String minzu;
    private Integer age;
    private String personInfoJson;
    private List currentAddress;
    private List currentCellNumbers;
    private List currentCarNumbers;
    private List warningInfoShortHands;
    //民族中文
    private String minzuCn;
    //性别中文
    private String sexCn;
    //行政区划中文
    private String xzqhCn;
    private String cardType;
    private String cardTypeCn;
    private Date  createTime;
    private String guoJi;
    private String guoJiCn;
    private Date updateTime;
    private String updateUser;
    private List warningInfoDetail;

    public List getWarningInfoDetail() {
        return warningInfoDetail;
    }

    public void setWarningInfoDetail(List warningInfoDetail) {
        this.warningInfoDetail = warningInfoDetail;
    }

    public String getPoliceDeptNo() {
        return policeDeptNo;
    }

    public void setPoliceDeptNo(String policeDeptNo) {
        this.policeDeptNo = policeDeptNo;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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

    public String getPoliceIdCard() {
        return policeIdCard;
    }

    public void setPoliceIdCard(String policeIdCard) {
        this.policeIdCard = policeIdCard;
    }

    public String getCheckTask() {
        return checkTask;
    }

    public void setCheckTask(String checkTask) {
        this.checkTask = checkTask;
    }

    public Boolean getTogether() {
        return together;
    }

    public void setTogether(Boolean together) {
        this.together = together;
    }

    public String getCheckObject() {
        return checkObject;
    }

    public void setCheckObject(String checkObject) {
        this.checkObject = checkObject;
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify;
    }

    public String getDisposalWay() {
        return disposalWay;
    }

    public void setDisposalWay(String disposalWay) {
        this.disposalWay = disposalWay;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationNameLevelOne() {
        return locationNameLevelOne;
    }

    public void setLocationNameLevelOne(String locationNameLevelOne) {
        this.locationNameLevelOne = locationNameLevelOne;
    }

    public String getLocationNameLevelTwo() {
        return locationNameLevelTwo;
    }

    public void setLocationNameLevelTwo(String locationNameLevelTwo) {
        this.locationNameLevelTwo = locationNameLevelTwo;
    }

    public String getLocationNameReal() {
        return locationNameReal;
    }

    public void setLocationNameReal(String locationNameReal) {
        this.locationNameReal = locationNameReal;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getCheckSourceType() {
        return checkSourceType;
    }

    public void setCheckSourceType(String checkSourceType) {
        this.checkSourceType = checkSourceType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouseHolds() {
        return houseHolds;
    }

    public void setHouseHolds(String houseHolds) {
        this.houseHolds = houseHolds;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getXzqh() {
        return xzqh;
    }

    public void setXzqh(String xzqh) {
        this.xzqh = xzqh;
    }

    public String getMinzu() {
        return minzu;
    }

    public void setMinzu(String minzu) {
        this.minzu = minzu;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPersonInfoJson() {
        return personInfoJson;
    }

    public void setPersonInfoJson(String personInfoJson) {
        this.personInfoJson = personInfoJson;
    }

    public List getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(List currentAddress) {
        this.currentAddress = currentAddress;
    }

    public List getCurrentCellNumbers() {
        return currentCellNumbers;
    }

    public void setCurrentCellNumbers(List currentCellNumbers) {
        this.currentCellNumbers = currentCellNumbers;
    }

    public List getCurrentCarNumbers() {
        return currentCarNumbers;
    }

    public void setCurrentCarNumbers(List currentCarNumbers) {
        this.currentCarNumbers = currentCarNumbers;
    }

    public List getWarningInfoShortHands() {
        return warningInfoShortHands;
    }

    public void setWarningInfoShortHands(List warningInfoShortHands) {
        this.warningInfoShortHands = warningInfoShortHands;
    }

    public String getMinzuCn() {
        return minzuCn;
    }

    public void setMinzuCn(String minzuCn) {
        this.minzuCn = minzuCn;
    }

    public String getSexCn() {
        return sexCn;
    }

    public void setSexCn(String sexCn) {
        this.sexCn = sexCn;
    }

    public String getXzqhCn() {
        return xzqhCn;
    }

    public void setXzqhCn(String xzqhCn) {
        this.xzqhCn = xzqhCn;
    }

    @Override
    public String toString() {
        return "UploadRequest{" +
                "policeIdCard='" + policeIdCard + '\'' +
                ", checkTask='" + checkTask + '\'' +
                ", together=" + together +
                ", checkObject='" + checkObject + '\'' +
                ", identify='" + identify + '\'' +
                ", disposalWay='" + disposalWay + '\'' +
                ", checkResult='" + checkResult + '\'' +
                ", location=" + location +
                ", locationNameLevelOne='" + locationNameLevelOne + '\'' +
                ", locationNameLevelTwo='" + locationNameLevelTwo + '\'' +
                ", locationNameReal='" + locationNameReal + '\'' +
                ", locationName='" + locationName + '\'' +
                ", checkSourceType='" + checkSourceType + '\'' +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", houseHolds='" + houseHolds + '\'' +
                ", sex='" + sex + '\'' +
                ", xzqh='" + xzqh + '\'' +
                ", minzu='" + minzu + '\'' +
                ", age=" + age +
                ", personInfoJson='" + personInfoJson + '\'' +
                ", currentAddress=" + currentAddress +
                ", currentCellNumbers=" + currentCellNumbers +
                ", currentCarNumbers=" + currentCarNumbers +
                ", warningInfoShortHands='" + warningInfoShortHands + '\'' +
                ", minzuCn='" + minzuCn + '\'' +
                ", sexCn='" + sexCn + '\'' +
                ", xzqhCn='" + xzqhCn + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardTypeCn='" + cardTypeCn + '\'' +
                '}';
    }
}
