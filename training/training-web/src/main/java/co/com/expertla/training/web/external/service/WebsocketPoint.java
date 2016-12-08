/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.training.model.dto.PlanMessageDTO;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;


@ServerEndpoint(value = "/chat/{sessionId}", encoders = MessageEncoder.class , decoders = MessageDecoder.class)
public class WebsocketPoint {

    private static final Logger LOGGER = Logger.getLogger(WebsocketPoint.class);

    @Inject
    private SessionRegistry sessionRegistry;

    @OnOpen
    public void open(Session session, @PathParam("sessionId") final Integer sessionId){
        //session.getBasicRemote().sendText("Hi Amigo David Lopera!");
        System.out.println("WebSocket opened: " + session.getId());
        session.getUserProperties().put("sessionId", sessionId);
        sessionRegistry.add(session);
    }
    
   /* public void send(@Observes PlanMessageDTO message) {
        System.out.println("Received : " + message);
        PlanMessageDTO msg = null;
        try {
            msg = planMessageService.saveMessage(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        sessionRegistry.getAll().forEach(session -> session.getAsyncRemote().sendObject(message));
        simpMessagingTemplate.convertAndSend("/queue/message/" + message.getCoachAssignedPlanId().getId(), msg);
    }*/

    @OnMessage
    public void onMessage(final Session session, final PlanMessageDTO message) {
        
        System.out.println("Received : " + message);
        sessionRegistry.getAll().forEach(sesion -> sesion.getAsyncRemote().sendObject(message));

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
