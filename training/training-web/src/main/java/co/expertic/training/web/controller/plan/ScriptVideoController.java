/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.controller.plan;

import co.expertic.training.model.dto.ChartReportDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.entities.PlanVideo;
import co.expertic.training.model.entities.ScriptVideo;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.plan.PlanVideoService;
import co.expertic.training.service.plan.ScriptVideoService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.List;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres Felipe Lopez
 */
@RestController
@RequestMapping("/script")
public class ScriptVideoController {
    
    private static final Logger LOGGER = Logger.getLogger(ScriptVideoController.class);
    
    @Autowired
    private ScriptVideoService scriptVideoService;
    
    @Autowired
    private PlanVideoService planVideoService;
    
    @RequestMapping(value = "get/getScriptVideoStarByCoach/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getScriptVideoByUserId(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ScriptVideo> planVideoList = scriptVideoService.getScriptVideoToStarId(userId);
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
    
    @RequestMapping(value = "get/getScriptVideoStarByStar/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getScriptVideoByStarId(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanVideo> planVideoList = planVideoService.getPlanVideoStarByStar(userId);
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
    
    @RequestMapping(value = "get/response/count/script/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getResponseCountScript(@PathVariable("userId") Integer userId,@PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ChartReportDTO> planVideoList = scriptVideoService.getResponseCountScripts(userId,roleId);
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
    
    @RequestMapping(value = "get/timeResponse/{userId}/{roleId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getTimeResponse(@PathVariable("userId") Integer userId,@PathVariable("roleId") Integer roleId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<PlanMessageDTO> planVideoList = scriptVideoService.getResponseTimeScripts(userId,roleId);
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

    @RequestMapping(value = "/get/by/plan/{planId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<ResponseService> getScriptsByPlan(@PathVariable("planId") Integer planId) {
        ResponseService responseService = new ResponseService();
        try {
           List<PlanVideoDTO> scripts = scriptVideoService.getByPlan(planId);

            responseService.setStatus(StatusResponse.SUCCESS.getName());
            responseService.setOutput(scripts);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            responseService.setOutput("Error al enviar video");
            responseService.setStatus(StatusResponse.FAIL.getName());
            responseService.setDetail(e.getMessage());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
