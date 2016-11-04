/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.web.controller.configuration.ActivityController;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.logging.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres Lopez
 */
@RestController
@RequestMapping("/strava")
public class StravaActivityController {
    /**
     * Crea activity <br>
     * Info. Creación: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activities", method = RequestMethod.POST, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivity(@RequestBody Activity activity) {
        ResponseService responseService = new ResponseService();
        try {
            responseService.setOutput(null);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
