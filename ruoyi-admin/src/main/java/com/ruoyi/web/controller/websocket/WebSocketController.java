//package com.ruoyi.web.controller.websocket;
//
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class WebSocketController {
//
//    @MessageMapping("/newOrder") // 定义消息的地址
//    @SendTo("/topic/orders") // 定义发送给订阅者的路径
//    public String orderNotification(String orderDetails) {
//        // 此处处理订单逻辑
//        return orderDetails;
//    }
//}
