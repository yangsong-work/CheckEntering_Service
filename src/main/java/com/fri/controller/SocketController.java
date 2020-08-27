package com.fri.controller;

import com.fri.socket.MyHandler;
import com.fri.utils.ResponseUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

@Controller
@Deprecated
public class SocketController {
    private MyHandler myHandler = new MyHandler();
    int i = 0;
    @RequestMapping("/sendMessage")
    @ResponseBody
    public Object sendMessage(String messageText) {
        boolean hasSend = false;
        if(i ==0) {
        TextMessage textMessage = new TextMessage("111111推送内容");
            hasSend = myHandler.sendMessageToUser("111111", textMessage);
            i++;
        }{
        TextMessage textMessage = new TextMessage("222222推送内容");
            hasSend = myHandler.sendMessageToUser("222222", textMessage);
        }
        System.out.println(hasSend);
        if (hasSend) {
            return ResponseUtil.ok();
        } else {
            return ResponseUtil.fail();
        }

    }
}
