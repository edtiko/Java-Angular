/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    
    @RequestMapping(value = "/create/athlete", method = RequestMethod.POST)
    public @ResponseBody
    Response create(@RequestBody CoachExtAthleteDTO dto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachExtAthleteService.create(dto);
            strResponse.append("Atleta creado éxitosamente.");
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
    
    @RequestMapping(value = "/sendInvitation", method = RequestMethod.POST)
    public @ResponseBody
    Response sendInvitation(@RequestBody CoachExtAthleteDTO dto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachExtAthleteService.sendInvitation(dto);
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
    
}
