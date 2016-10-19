/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.CoachAssignedPlanService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CoachAssignedPlanController {
    
    private static final Logger LOGGER = Logger.getLogger(CoachAssignedPlanController.class);
    
    @Autowired
    CoachAssignedPlanService coachService;
    
    @Autowired
    CoachExtAthleteService coachExtService;
    
    
    @RequestMapping(value = "get/athtletes/{coachUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAssignedAthletes(@PathVariable("coachUserId") Integer coachUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<CoachAssignedPlanDTO> athletes = coachService.findByCoachUserId(coachUserId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "get/coach/{athleteUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAssignedCoach(@PathVariable("athleteUserId") Integer athleteUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            CoachAssignedPlanDTO assignedCoachInternal = coachService.findByAthleteUserId(athleteUserId);
            CoachExtAthleteDTO assignedCoachExternal = coachExtService.findByAthleteUserId(athleteUserId);
            if (assignedCoachInternal != null) {
                assignedCoachInternal.setExternal(false);
                responseService.setOutput(assignedCoachInternal);
            } else if (assignedCoachExternal != null) {
                assignedCoachExternal.setExternal(true);
                responseService.setOutput(assignedCoachExternal);
            } else {
                responseService.setStatus(StatusResponse.FAIL.getName());
                responseService.setOutput("El usuario no tiene asociado un plan activo.");
                return Response.status(Response.Status.OK).entity(responseService).build();
            }
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(responseService).build();
        }

    }
    
    @RequestMapping(value = "get/athtletes/by/star/{starUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAssignedAthletesByStarUserId(@PathVariable("starUserId") Integer starUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<CoachAssignedPlanDTO> athletes = coachService.findByStarUserId(starUserId);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
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
