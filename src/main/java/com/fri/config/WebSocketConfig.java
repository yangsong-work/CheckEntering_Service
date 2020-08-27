package com.fri.config;

import com.fri.socket.MyHandler;
import com.fri.socket.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 *
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Autowired
    MyHandler myHandler;

    @Value("${socket.url}")
    private String socketUrl;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(myHandler, socketUrl).addInterceptors(new WebSocketInterceptor()).setAllowedOrigins("*");
    }
//    @Bean
//    public WebSocketHandler myHandler(){
//        return new MyHandler();
//    }

}

