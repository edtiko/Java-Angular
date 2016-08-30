/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    
    @Autowired
    private PlanMessageService planMessageService;
    
    @Autowired 
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{sessionId}")
    //@SendTo("/topic/message")
    public void sendMessage(PlanMessageDTO message, @DestinationVariable("sessionId") Integer sessionId) {
        PlanMessageDTO msg = null;
        try {
          msg  = planMessageService.saveMessage(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
         
        }
        simpMessagingTemplate.convertAndSend("/queue/message/"+sessionId, msg);
        //return new OutputMessage(message, new Date());
    }
    
    @RequestMapping(value = "/get/messages/{coachAssignedPlanId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByPlan(coachAssignedPlanId);
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
    
     @RequestMapping(value = "/get/count/available/messages/{coachAssignedPlanId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAvailableMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer count = planMessageService.getCountMessagesByPlan(coachAssignedPlanId);
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

}
