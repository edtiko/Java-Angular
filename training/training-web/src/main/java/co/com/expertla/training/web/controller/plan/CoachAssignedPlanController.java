/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.CoachAssignedPlanService;
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
    
    @RequestMapping(value = "get/athtletes/{coachUserId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getAssignedAthletes(@PathVariable("coachUserId") Integer coachUserId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<CoachAssignedPlanDTO> athletes = coachService.findByCoachUserId(coachUserId);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.SUCCESS.getName());
            responseService.setOutput(athletes);
            return Response.status(Response.Status.OK).entity(responseService).build();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput(strResponse);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.FAIL.getName());
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
            CoachAssignedPlanDTO assignedCoach = coachService.findByAthleteUserId(athleteUserId);
            responseService.setStatus(co.com.expertla.training.enums.StatusResponse.SUCCESS.getName());
            responseService.setOutput(assignedCoach);
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
