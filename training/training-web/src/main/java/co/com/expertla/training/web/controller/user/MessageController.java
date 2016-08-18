/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.user;

import co.com.expertla.training.model.dto.OutputMessage;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.PlanMessageService;
import java.util.Date;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    PlanMessageService planMessageService;

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public Response sendMessage(PlanMessageDTO message) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planMessageService.saveMessage(message);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.SUCCESS.getName());
            responseService.setOutput(new OutputMessage(message, new Date()));
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
     
        
    }
    
    @RequestMapping(value = "/get/messages/{coachAssignedPlanId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByPlan(coachAssignedPlanId);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }

}
