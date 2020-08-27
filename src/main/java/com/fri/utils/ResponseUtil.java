package com.fri.utils;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {

  public static String ok() {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", "0");
    obj.put("msg", "成功");
    return JSON.toJSONString(obj);
  }

  public static String ok(Object data) {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", "0");
    obj.put("msg", "成功");
    obj.put("data", data);
    return JSON.toJSONString(obj);
  }

  public static String ok(String errmsg, Object data) {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", "0");
    obj.put("msg", errmsg);
    obj.put("data", data);
    return JSON.toJSONString(obj);
  }
  public static String ok(String errmsg) {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", "0");
    obj.put("msg", errmsg);
    return JSON.toJSONString(obj);
  }

  public static String fail() {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", "1");
    obj.put("msg", "错误");
    return JSON.toJSONString(obj);
  }

  public static String fail(String errno, String errmsg) {
    Map<String, Object> obj = new HashMap<String, Object>();
    obj.put("code", errno);
    obj.put("msg", errmsg);
    return JSON.toJSONString(obj);
  }



  public static String fail402() {
    return fail("402", "请求参数错误");
  }

  public static String fail404() {
    return fail("404", "找不到对应资源");
  }

  public static String fail501() {
    return fail("501", "业务不支持");
  }

  public static String unsupport() {
    return fail501();
  }

  public static String fail502() {
    return fail("502", "系统内部错误");
  }




}

