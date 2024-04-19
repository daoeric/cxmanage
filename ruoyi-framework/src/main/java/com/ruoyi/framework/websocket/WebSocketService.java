//package com.ruoyi.framework.websocket;
//
//import com.ruoyi.customer.domain.TWithdrawRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class WebSocketService {
//
//
//    @Autowired
//    private SimpMessagingTemplate template;
//
//
//    public void newOrderReceived(TWithdrawRequest order) {
//        // 通知WebSocket客户端
//        template.convertAndSend("/topic/newOrder", order);
//    }
//}
