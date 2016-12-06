/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import javax.websocket.EndpointConfig;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
@ServerEndpoint("/hello")
public class WebsocketPoint {
    
    @Inject
   private SessionRegistry sessionRegistry;

    @OnOpen
    public void open(Session session, EndpointConfig conf) throws IOException {
        session.getBasicRemote().sendText("Hi Amigo David Lopera!");
        System.out.println("WebSocket opened: " + session.getId());
        sessionRegistry.add(session);
    }

    @OnMessage
    public String hello(String message) {
        System.out.println("Received : " + message);
        sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendText(message));
        return message;
    }

    @OnClose
    public void myOnClose(Session session, CloseReason reason) {
          sessionRegistry.remove(session);
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }
}
