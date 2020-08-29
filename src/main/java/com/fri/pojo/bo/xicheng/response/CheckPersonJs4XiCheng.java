package com.fri.pojo.bo.xicheng.response;

public class CheckPersonJs4XiCheng {
    //value 疑似精神异常人员
    private String DATA_CLASS;
    //LOCAL_LABEL_110102760000label2
    private String resourceName;
    //异常等级 3
    private String ALARM_LEVEL;

    public String getDATA_CLASS() {
        return DATA_CLASS;
    }

    public void setDATA_CLASS(String DATA_CLASS) {
        this.DATA_CLASS = DATA_CLASS;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getALARM_LEVEL() {
        return ALARM_LEVEL;
    }

    public void setALARM_LEVEL(String ALARM_LEVEL) {
        this.ALARM_LEVEL = ALARM_LEVEL;
    }

    @Override
    public String toString() {
        return "CheckPersonJs4XiCheng{" +
                "DATA_CLASS='" + DATA_CLASS + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", ALARM_LEVEL='" + ALARM_LEVEL + '\'' +
                '}';
    }
}
