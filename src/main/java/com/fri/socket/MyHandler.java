package com.fri.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.fri.controller.APPWebController;
import com.fri.service.CheckEnterService;
import com.fri.utils.ResponseUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
@Component
public class MyHandler extends TextWebSocketHandler {
    @Autowired
    CheckEnterService checkEnterService;
    private static Logger log = LoggerFactory.getLogger(MyHandler.class);
    //在线用户列表
    private static final Map<String, WebSocketSession> users;

    //类加载初始化一个map集合，存放用户的websocket对象
    static {

        users = new LinkedHashMap<String, WebSocketSession>();

    }

    /**
     * 获取用户标识,获取websocekt对象的map集合
     *
     */
    private String getClientId(WebSocketSession session) {
        try {
            //获取存入websocket的userid
            String padId = "";
            padId = (String) session.getAttributes().get("padId");
            return padId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 成功建立连接触发的方法，
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        String result = ResponseUtil.ok("成功建立socket连接");
        log.info("收到客户端发起连接请求：{}", result);
        session.sendMessage(new TextMessage(result));

    }

    /**
     * 当接收到客户端浏览器后接收的方法(仅限绑定)
     */
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // ...
        try {
            Map<String, String> map = JSON.parseObject(message.getPayload(), Map.class);
            String padId = map.get("padId");
            //无法获取padId设备号，绑定失败
            if (StringUtils.isBlank(padId)) {
                bindfailure(session);
                return;
            }

            log.info("收到客户端绑定请求：{}", padId);
            users.put(padId, session);
            String result = ResponseUtil.ok("绑定成功");
            log.info("与客户端成功绑定：{}", result);
            WebSocketMessage returnMessage = new TextMessage(result);
            session.sendMessage(returnMessage);
            session.getAttributes().put("padId",padId);
            //TODO 通知核录桩绑定成功
          //  checkEnterService.notifyLogin(padId);
        } catch (JSONException e) {
            e.printStackTrace();
            //JSON格式不正确，绑定失败
            bindfailure(session);
            return;

        }
    }

    /**
     * 发送信息给指定用户
     *
     * @param padId
     * @param message
     * @return map中根据用户的id获取对应得websoket，发送信息
     */
    public boolean sendMessageToUser(String padId, TextMessage message) {
        //获取当前的用户
        if (users.get(padId) == null) {
            return false;
        }
        WebSocketSession session = users.get(padId);
        // log.info("向APP推送数据:{}" , message);
        if (!session.isOpen()) return false;
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * 广播信息（发送给所有人）
     *
     * @param message
     * @return 遍历出map中所有的websocket发送在线消息
     */
    public boolean sendMessageToAllUsers(TextMessage message) {
        boolean allSendSuccess = true;
        Set<String> userIds = users.keySet();
        WebSocketSession session = null;
        for (String userId : userIds) {
            try {
                session = users.get(userId);
                if (session.isOpen()) {
                    session.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
                allSendSuccess = false;
            }
        }

        return allSendSuccess;
    }

    /**
     * 当链接关闭后触发的方法，连接已关闭，移除在Map集合中的记录。
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        users.remove(getClientId(session));
        log.info("连接已关闭：" + status); //当前的状态码，并删除存储在map中的websocket的链接对象
        //TODO 是否通知核录桩？
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void bindfailure(WebSocketSession session) throws IOException {
        String result = ResponseUtil.fail("1", "无法获取核录桩设备号，绑定失败");
        log.info("与客户端绑定失败：{}", result);
        WebSocketMessage returnMessage = new TextMessage(result);
        session.sendMessage(returnMessage);
    }
}