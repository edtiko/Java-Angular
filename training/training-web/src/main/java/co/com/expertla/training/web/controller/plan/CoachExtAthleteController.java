/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.CoachExtAthleteService;
import co.com.expertla.training.web.enums.StatusResponse;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
    Response create(CoachExtAthleteDTO dto) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            coachExtAthleteService.create(dto);
            strResponse.append("creado éxitosamente.");
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
