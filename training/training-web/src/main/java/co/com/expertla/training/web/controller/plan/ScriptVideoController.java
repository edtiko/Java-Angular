/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.controller.plan;

import co.com.expertla.training.model.entities.PlanVideo;
import co.com.expertla.training.model.entities.ScriptVideo;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.ScriptVideoService;
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
 * @author Andres Felipe Lopez
 */
@RestController
@RequestMapping("/script")
public class ScriptVideoController {
    
    private static final Logger LOGGER = Logger.getLogger(ScriptVideoController.class);
    
    @Autowired
    private ScriptVideoService scriptVideoService;
    
    @RequestMapping(value = "get/getScriptVideoStarByCoach/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    Response getScriptVideoByUserId(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        StringBuilder strResponse = new StringBuilder();
        try {
            List<ScriptVideo> planVideoList = scriptVideoService.getScriptVideoByUserId(userId);
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
}
