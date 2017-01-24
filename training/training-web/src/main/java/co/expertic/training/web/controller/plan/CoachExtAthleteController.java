/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.plan;

import co.expertic.training.model.dto.CoachExtAthleteDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.plan.CoachExtAthleteService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/externalCoach")
public class CoachExtAthleteController {
    
    private static final Logger LOGGER = Logger.getLogger(CoachExtAthleteController.class);
    
    @Autowired
    CoachExtAthleteService coachExtAthleteService;
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    
    @RequestMapping(value = "/create/athlete", method = RequestMethod.POST)
    public @ResponseBody
    Response create(@RequestBody CoachExtAthleteDTO dto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        Integer count = 0;
        try {
            /*count = coachExtAthleteService.getCountAthletesAvailable(dto.getTrainingPlanUserId());

            if (count == 0) {
                strResponse.append("Ya consumió el limite de atletas permitidos.");
                responseService.setOutput(strResponse);
                responseService.setStatus(StatusResponse.FAIL.getName());
                return Response.status(Response.Status.OK).entity(responseService).build();
            }*/
            coachExtAthleteService.create(dto);
            simpMessagingTemplate.convertAndSend("/queue/invitation/" + dto.getAthleteUserId().getUserId(), dto);
            strResponse.append("Atleta creado éxitosamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "/sendInvitation", method = RequestMethod.POST)
    public @ResponseBody
    Response sendInvitation(@RequestBody CoachExtAthleteDTO dto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachExtAthleteService.sendInvitation(dto);
            simpMessagingTemplate.convertAndSend("/queue/invitation/" + dto.getAthleteUserId().getUserId(), dto);
            strResponse.append("Se ha enviado la invitación éxitosamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "/get/athletes/{trainingPlanUserId}/{state}", method = RequestMethod.GET)
    public ResponseEntity<List<CoachExtAthleteDTO>> getAthletes(@PathVariable("trainingPlanUserId") Integer trainingPlanUserId, @PathVariable("state") String state) {
        try {
            List<CoachExtAthleteDTO> athletes = coachExtAthleteService.getAthletes(trainingPlanUserId, state);
            if (athletes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<>(athletes, HttpStatus.OK);
        } catch (Exception e) {
             LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/get/user/athletes/{query}", method = RequestMethod.GET)
    public ResponseEntity<List<UserDTO>> getUserAthletes(@PathVariable("query") String query) {
        try {
            List<UserDTO> athletes = coachExtAthleteService.getUserAthletes(query);
            if (athletes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<>(athletes, HttpStatus.OK);
        } catch (Exception e) {
             LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    
    @RequestMapping(value = "/delete/athlete/{coachExtAthleteId}", method = RequestMethod.GET)
    public @ResponseBody
    Response removeAthleteCoach(@PathVariable("coachExtAthleteId") Integer coachExtAthleteId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachExtAthleteService.retireAthlete(coachExtAthleteId);
            strResponse.append("Atleta retirado éxitosamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
     @RequestMapping(value = "/get/invitation/{userId}", method = RequestMethod.GET)
    public ResponseEntity<CoachExtAthleteDTO> getInvitation(@PathVariable("userId") Integer userId) {
        try {
            CoachExtAthleteDTO invitation = coachExtAthleteService.getInvitation(userId);
            if (invitation == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
            }
            return new ResponseEntity<>(invitation, HttpStatus.OK);
        } catch (Exception e) {
             LOGGER.error(e.getMessage(), e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @RequestMapping(value = "/accept/invitation/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Response acceptInvitation(@PathVariable("id") Integer coachExtAthleteId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            Integer trainingPlanUserId = coachExtAthleteService.acceptInvitation(coachExtAthleteId);
            simpMessagingTemplate.convertAndSend("/queue/invitation/" + trainingPlanUserId, "");
            strResponse.append("Invitación aceptada éxitosamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "/reject/invitation/{id}", method = RequestMethod.GET)
    public @ResponseBody
    Response rejectInvitation(@PathVariable("id") Integer coachExtAthleteId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
             Integer trainingPlanUserId =  coachExtAthleteService.rejectInvitation(coachExtAthleteId);
             simpMessagingTemplate.convertAndSend("/queue/invitation/" + trainingPlanUserId, "");
            strResponse.append("Invitación rechazada éxitosamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(strResponse);
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
