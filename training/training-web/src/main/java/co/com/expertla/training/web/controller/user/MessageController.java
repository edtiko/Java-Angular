package co.com.expertla.training.web.controller.user;

import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.PlanMessageService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Edwin G
 */
@RestController
public class MessageController {

    private static final Logger LOGGER = Logger.getLogger(MessageController.class);
    private static final String COACH_INTERNO = "IN";
    private static final String COACH_EXTERNO = "EXT";

    @Autowired
    private PlanMessageService planMessageService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{sessionId}")
    //@SendTo("/topic/message")
    public void sendMessage(PlanMessageDTO message, @DestinationVariable("sessionId") Integer sessionId) {
        PlanMessageDTO msg = null;
        try {
            msg = planMessageService.saveMessage(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/message/" + sessionId, msg);
        //return new OutputMessage(message, new Date());
    }

    @RequestMapping(value = "/get/messages/{coachAssignedPlanId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByPlan(coachAssignedPlanId, tipoPlan);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/get/count/available/messages/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer count = 0;
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planMessageService.getCountMessagesByPlan(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planMessageService.getCountMessagesByPlanExt(coachAssignedPlanId, userId);
            }

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/get/count/received/messages/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessagesReceived(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer count = 0;
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planMessageService.getCountMessagesReceived(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planMessageService.getCountMessagesReceivedExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/read/messages/{coachAssignedPlanId}/{userId}/{tipoPlan}", method = RequestMethod.GET)
    public @ResponseBody
    Response readMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, @PathVariable("tipoPlan") String tipoPlan) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                planMessageService.readMessages(coachAssignedPlanId, userId);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                planMessageService.readMessagesExt(coachAssignedPlanId, userId);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Mensajes Leidos Correctamente.");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

    @RequestMapping(value = "/read/message/{planMessageId}", method = RequestMethod.GET)
    public @ResponseBody
    Response readMessage(@PathVariable("planMessageId") Integer planMessageId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planMessageService.readMessage(planMessageId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Mensaje Leido Correctamente.");
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "/get/messages/by/receivingUser/sendingUser/{recevingUserId}/{sendingUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessagesByReceivingUserAndSendingUser(@PathVariable("recevingUserId") Integer recevingUserId,@PathVariable("sendingUserId") Integer sendingUserId ) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByReceivingUserAndSendingUser(recevingUserId,sendingUserId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

}
