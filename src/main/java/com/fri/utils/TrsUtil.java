package com.fri.utils;

import com.fri.socket.MyHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
@Component
public class TrsUtil {
    @Autowired
    MyHandler myHandler;

    //推送内容
    public void sendMessage(String padId,String message) {
        boolean hasSend = false;
        TextMessage textMessage = new TextMessage(message);
        hasSend = myHandler.sendMessageToUser(padId,textMessage);
        System.out.println(hasSend);
        if (hasSend) {
         //TODO   return ResponseUtil.ok();
        } else {
          //  return ResponseUtil.fail();
        }

    }

}
