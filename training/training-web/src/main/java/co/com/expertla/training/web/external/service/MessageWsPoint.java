package co.com.expertla.training.web.external.service;

import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.service.plan.PlanMessageService;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.apache.log4j.Logger;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.server.standard.SpringConfigurator;

@ServerEndpoint(value = "/chat/{sessionId}",
        configurator = SpringConfigurator.class,
        encoders = MessageEncoder.class,
        decoders = MessageDecoder.class)
public class MessageWsPoint {

    private static final Logger LOGGER = Logger.getLogger(MessageWsPoint.class);

    private final PlanMessageService messageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public MessageWsPoint(PlanMessageService messageService) {
        this.messageService = messageService;
    }

    @Autowired
    private SessionRegistry sessionRegistry;

    @OnOpen
    public void open(Session session, @PathParam("sessionId") final Integer sessionId) {
        System.out.println("WebSocket opened: " + session.getId());
        session.getUserProperties().put("sessionId", sessionId);
        sessionRegistry.add(session);
    }

    @OnMessage
    public void onMessage(final Session session, final PlanMessageDTO message) {

        System.out.println("Received : " + message);
        if (message != null && message.isMobile()) {
            PlanMessageDTO msg = null;
            try {
                msg = messageService.saveMessage(message);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);

            }
            simpMessagingTemplate.convertAndSend("/queue/message/" + session.getUserProperties().get("sessionId"), msg);
        }

        sessionRegistry.getAll().forEach(sesion -> sesion.getAsyncRemote().sendObject(message));

    }

    @OnClose
    public void myOnClose(Session session, CloseReason reason) {
        sessionRegistry.remove(session);
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

}