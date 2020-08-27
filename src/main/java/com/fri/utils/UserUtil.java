package com.fri.utils;

import com.fri.model.CheckEnterPushInfo;
import com.fri.model.PoliceLoginRecord;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserUtil {
    //警员 key deviceNo
    private static final Map<String, PoliceLoginRecord> userMap;
    //PAD推送記錄 key padId value 推送顯示記錄
    private static final Map<String, CheckEnterPushInfo> padPushMap;

    //类加载初始化一个map集合
    static {
        userMap = new ConcurrentHashMap<String, PoliceLoginRecord>();
        padPushMap = new ConcurrentHashMap<String, CheckEnterPushInfo>();
    }

    public static Map<String, PoliceLoginRecord> getUserMap() { return userMap; }
    public static Map<String, CheckEnterPushInfo> getPadPushMap() { return padPushMap; }
}
