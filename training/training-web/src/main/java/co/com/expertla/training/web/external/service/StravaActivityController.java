/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.web.controller.configuration.ActivityController;
import co.com.expertla.training.web.enums.StatusResponse;
import java.io.IOException;
import java.util.logging.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Andres Lopez
 */
@RestController
@RequestMapping("/strava")
public class StravaActivityController {
    
    public static final String CLIENT_ID = "14512";
    public static final String CLIENT_SECRET = "aa52ba6596961f92a9c03b79b484e31e4b88af2c";
    
    private String getToken(String clientId, String clientSecret, String code) throws IOException {
        CurlService curlService = new CurlService();
        String param = "?client_id="+clientId+"&client_secret="+clientSecret+"&code="+code;
        String res = curlService.sendPost("https://www.strava.com/oauth/token"+param, null, false, null);
        System.out.println("res " + res);
        return res;
    }
    
    /**
     * Obtiene actividades de strava <br>
     * Info. Creación: <br>
     * fecha 04/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param codeAuth
     * @return
     */
    @RequestMapping(value = "activities", method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getActivityList(@RequestParam("code") String codeAuth) {
        ResponseService responseService = new ResponseService();
        try {
            CurlService curlService = new CurlService();
            String response = curlService.sendPost("https://www.strava.com/api/v3/activities", null, true, getToken(CLIENT_ID, CLIENT_SECRET, codeAuth));
            responseService.setOutput(response);
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
