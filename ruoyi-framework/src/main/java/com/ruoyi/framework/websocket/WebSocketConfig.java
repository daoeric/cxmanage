//package com.ruoyi.framework.websocket;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
///**
// * websocket 配置
// *
// * @author ruoyi
// */
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
//{
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        // 注册一个WebSocket端点
//        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
//    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        // 配置消息代理，以"/topic"为前缀的目的地将会路由到消息代理
//        registry.enableSimpleBroker("/topic");
//        // 配置一个或多个前缀来过滤目标应用
//        registry.setApplicationDestinationPrefixes("/app");
//    }
//
//
//}
