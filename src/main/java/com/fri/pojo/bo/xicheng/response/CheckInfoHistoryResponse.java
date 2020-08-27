package com.fri.pojo.bo.xicheng.response;

public class CheckInfoHistoryResponse extends BaseResponse {

      private Integer count;
      //1-排除  2-留置 3-存疑
      private String chuzhiname;
      //1-排除  2-留置 3-存疑
      private String cztype;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getChuzhiname() {
        return chuzhiname;
    }

    public void setChuzhiname(String chuzhiname) {
        this.chuzhiname = chuzhiname;
    }

    public String getCztype() {
        return cztype;
    }

    public void setCztype(String cztype) {
        this.cztype = cztype;
    }

    @Override
    public String toString() {
        return "CheckInfoHistoryResponse{" +
                "count=" + count +
                ", chuzhiname='" + chuzhiname + '\'' +
                ", cztype='" + cztype + '\'' +
                '}';
    }
}
