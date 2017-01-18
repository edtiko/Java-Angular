/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.external.service;

import co.expertic.training.model.util.CurlService;
import co.expertic.base.util.DateUtil;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.ActivityPerformanceMetafield;
import co.expertic.training.model.entities.User;
import co.expertic.training.model.entities.UserActivityPerformance;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.user.StravaService;
import co.expertic.training.service.user.UserActivityPerformanceService;
import co.expertic.training.service.user.UserService;
import co.expertic.training.web.controller.configuration.ActivityController;
import co.expertic.training.web.enums.StatusResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Andres Lopez
 */
@RestController
@RequestMapping("/strava")
public class StravaActivityController {

    @Autowired
    UserService userService;
    
    @Autowired
    StravaService stravaService;

    @Autowired
    UserActivityPerformanceService activityPerformanceService;

    /**
     * Obtiene actividades de strava <br>
     * Info. Creación: <br>
     * fecha 04/11/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param state
     * @param codeAuth
     * @param error
     * @param session
     * @param request
     * @param httpResponse
     * @return
     * @throws java.io.IOException
     */
    @RequestMapping(value = "activities", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> getActivityList(
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "code", required = false) String codeAuth,
            @RequestParam(value = "error", required = false) String error,
            HttpSession session, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        ResponseService responseService = new ResponseService();
        try {

            if (error != null && !error.isEmpty()) {
                httpResponse.sendRedirect(request.getRequestURL() + "/../../#/data-person");
            }
            CurlService curlService = new CurlService();
            UserDTO userId = (UserDTO) session.getAttribute("user");
            UserDTO objUser = userService.findById(userId.getUserId());
            objUser.setIndStrava("1");
            objUser.setCodeStrava(codeAuth);
            String tokenResponse = stravaService.getToken(curlService, codeAuth);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse);
            String token = jo.get("access_token").getAsString();
            String tokenType = jo.get("token_type").getAsString();
            curlService = new CurlService(tokenType);
            String params = "";

            if (objUser.getLastExecuteStrava() != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(objUser.getLastExecuteStrava());
                long time = c.getTimeInMillis() / 1000L;
                params = "?after=" + time;
            }
            
            String response = curlService.sendGet(StravaService.STRAVA_URL + "api/v3/athlete/activities" + params, null, true, token);
            List<UserActivityPerformance> list = stravaService.getMetaField(curlService, token,
                    response, objUser);

            for (UserActivityPerformance userActivityPerformance : list) {
                activityPerformanceService.create(userActivityPerformance);
            }

            httpResponse.sendRedirect(request.getRequestURL() + "/../../#/data-person");
            return null;
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StravaActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            httpResponse.sendRedirect(request.getRequestURL() + "/../../#/data-person");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
