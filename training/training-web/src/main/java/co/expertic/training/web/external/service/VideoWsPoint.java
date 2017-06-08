package co.expertic.training.web.external.service;

import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.service.plan.PlanVideoService;
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

@ServerEndpoint(value = "/videows/{sessionId}",
        configurator = SpringConfigurator.class,
        encoders = VideoEncoder.class,
        decoders = VideoDecoder.class)
public class VideoWsPoint {

    private static final Logger LOGGER = Logger.getLogger(VideoWsPoint.class);

    private final PlanVideoService videoService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public VideoWsPoint(PlanVideoService videoService) {
        this.videoService = videoService;
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
    public void onMessage(final Session session, final PlanVideoDTO message) {

        if (message != null && message.isMobile()) {

            simpMessagingTemplate.convertAndSend("/queue/video", message);
        }

        sessionRegistry.getAll().forEach(sesion -> sesion.getAsyncRemote().sendObject(message));

    }

    @OnClose
    public void myOnClose(Session session, CloseReason reason) {
        sessionRegistry.remove(session);
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

}
