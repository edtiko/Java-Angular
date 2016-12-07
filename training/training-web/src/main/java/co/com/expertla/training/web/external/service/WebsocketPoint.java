/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.training.model.dto.PlanMessageDTO;
import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@ServerEndpoint(value = "/chat/{sessionId}", encoders={JSONEncoder.class})
public class WebsocketPoint {
    
    @Inject
   private SessionRegistry sessionRegistry;
    
        @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @OnOpen
    public void open(Session session, EndpointConfig conf) throws IOException {
        //session.getBasicRemote().sendText("Hi Amigo David Lopera!");
        System.out.println("WebSocket opened: " + session.getId());
        sessionRegistry.add(session);
    }

    @OnMessage
    public void hello(@PathParam("sessionId") Integer sessionId, PlanMessageDTO message) {
        System.out.println("Received : " + message);
        sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendObject(message));
        simpMessagingTemplate.convertAndSend("/queue/message/" + sessionId, message);

    }

    @OnClose
    public void myOnClose(Session session, CloseReason reason) {
          sessionRegistry.remove(session);
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }
    
   /*    public void send(@Observes PlanMessageDTO msg) {
       sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendObject(msg)); 
   }*/
}
