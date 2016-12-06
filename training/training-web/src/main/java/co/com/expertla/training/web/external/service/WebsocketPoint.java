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
@ServerEndpoint("/hello")
public class WebsocketPoint {
   @OnOpen
   public void open(Session session, EndpointConfig conf) throws IOException {
       session.getBasicRemote().sendText("Hi Amigo David Lopera!");
   }
}
