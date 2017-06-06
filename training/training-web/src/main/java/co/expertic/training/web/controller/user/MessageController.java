package co.expertic.training.web.controller.user;

import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.plan.CoachAssignedPlanService;
import co.expertic.training.service.plan.PlanMessageService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
    private CoachAssignedPlanService coachAssignedPlanService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat")
    //@SendTo("/topic/message")
    public void sendMessage(PlanMessageDTO message) {
        PlanMessageDTO msg = null;
        try {
            msg = planMessageService.saveMessage(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/message", msg);
        //return new OutputMessage(message, new Date());
    }
    
     @RequestMapping(value="chat/mobile/{sessionId}",method = RequestMethod.POST)
    public void sendMessageMobile(PlanMessageDTO message, @PathVariable("sessionId") Integer sessionId) {
        PlanMessageDTO msg = null;
        try {
            msg = planMessageService.saveMessage(message);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);

        }
        simpMessagingTemplate.convertAndSend("/queue/message/" + sessionId, msg);
    }

    @RequestMapping(value = "get/messages/{planId}/{tipoPlan}/{roleSelected}/{userSessionId}/{receivingUserId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getMessages(@PathVariable("planId") Integer planId, @PathVariable("tipoPlan") String tipoPlan, 
                                               @PathVariable("roleSelected") Integer roleSelected, @PathVariable("userSessionId") Integer userId,
                                               @PathVariable("receivingUserId") Integer receivingUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByPlan(planId, tipoPlan, roleSelected, userId, receivingUserId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "get/count/available/messages/{coachAssignedPlanId}/{userId}/{toUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
     ResponseEntity<ResponseService> getAvailableMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                                  @PathVariable("toUserId") Integer toUserId, @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        Integer emergency = 0;
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planMessageService.getCountMessagesByPlan(coachAssignedPlanId, userId,toUserId, roleSelected);
                emergency = planMessageService.getCountMessagesEmergency(coachAssignedPlanId, userId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planMessageService.getCountMessagesByPlanExt(coachAssignedPlanId, userId);
                emergency = planMessageService.getCountMessagesEmergencyExt(coachAssignedPlanId, userId);
            }

              responseService.setStatus(StatusResponse.SUCCESS.getName());
              responseService.setOutput(count == 0?emergency:count);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
           return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "get/count/received/messages/{coachAssignedPlanId}/{userId}/{toUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getMessagesReceived(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId, 
                                 @PathVariable("toUserId") Integer toUserId,
                                 @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer count = 0;
            if (tipoPlan.equals(COACH_INTERNO)) {
                count = planMessageService.getCountMessagesReceived(coachAssignedPlanId, userId, toUserId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                count = planMessageService.getCountMessagesReceivedExt(coachAssignedPlanId, userId);
            }else if(userId != -1 && toUserId == -1){
                count = planMessageService.getCountMessagesReceivedByUser(userId);
            }else if(userId != -1 && toUserId != -1){
                count = planMessageService.getCountMessagesReceived(coachAssignedPlanId, userId, toUserId, roleSelected);  
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(count);
              return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
              return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "read/messages/{coachAssignedPlanId}/{userId}/{toUserId}/{tipoPlan}/{roleSelected}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> readMessages(@PathVariable("coachAssignedPlanId") Integer coachAssignedPlanId, @PathVariable("userId") Integer userId,
                          @PathVariable("toUserId") Integer toUserId,
                          @PathVariable("tipoPlan") String tipoPlan, @PathVariable("roleSelected") Integer roleSelected) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            if (tipoPlan.equals(COACH_INTERNO)) {
                planMessageService.readMessages(coachAssignedPlanId, userId,toUserId, roleSelected);
            } else if (tipoPlan.equals(COACH_EXTERNO)) {
                planMessageService.readMessagesExt(coachAssignedPlanId, userId);
            }else{
                 planMessageService.readMessages(coachAssignedPlanId, userId,toUserId, roleSelected);
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Mensajes Leidos Correctamente.");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
           return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }

    @RequestMapping(value = "read/message/{planMessageId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> readMessage(@PathVariable("planMessageId") Integer planMessageId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planMessageService.readMessage(planMessageId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("Mensaje Leido Correctamente.");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    @RequestMapping(value = "get/messages/by/receivingUser/sendingUser/{recevingUserId}/{sendingUserId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getMessagesByReceivingUserAndSendingUser(@PathVariable("recevingUserId") Integer recevingUserId,@PathVariable("sendingUserId") Integer sendingUserId ) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> messages = planMessageService.getMessagesByReceivingUserAndSendingUser(recevingUserId,sendingUserId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    @RequestMapping(value = "chat/get/response/count/chat/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getResponseCountVideo(@PathVariable("userId") Integer userId,@PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ChartReportDTO> planVideoList = planMessageService.getResponseCountMessages(userId,roleId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(planVideoList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "chat/get/timeResponse/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getTimeResponse(@PathVariable("userId") Integer userId,@PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> planVideoList = planMessageService.getResponseTimeMessages(userId,roleId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(planVideoList);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }
    }
    
    @RequestMapping(value = "set/star/manage/message/{planId}", method = RequestMethod.GET)
    public @ResponseBody
     ResponseEntity<ResponseService>  setStarManageMessages(@PathVariable("planId") Integer planId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachAssignedPlanService.setStarManageMessages(planId); 
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput("");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
     
    @RequestMapping(value = "resend/star/messages/{planId}", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<ResponseService> resendStarMessages(@PathVariable("planId") Integer planId, @RequestBody PlanMessageDTO messages) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            planMessageService.resendStarMessages(planId, messages.getStarMessages());
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(messages);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }
    
    @RequestMapping(value = "get/user/messages/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getUserMessages(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<UserResumeDTO> userMessages = planMessageService.getMessageUsersByUserId(userId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(userMessages);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }

    }


}
