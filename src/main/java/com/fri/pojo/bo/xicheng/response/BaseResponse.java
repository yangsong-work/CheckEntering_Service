package com.fri.pojo.bo.xicheng.response;

public class BaseResponse {
    private Integer status;
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseResponseVO{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
