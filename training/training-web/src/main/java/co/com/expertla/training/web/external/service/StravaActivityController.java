/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserActivityPerformance;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.web.controller.configuration.ActivityController;
import co.com.expertla.training.web.enums.StatusResponse;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Andres Lopez
 */
@RestController
@RequestMapping("/strava")
public class StravaActivityController {

    public static final String CLIENT_ID = "14512";
    public static final String CLIENT_SECRET = "aa52ba6596961f92a9c03b79b484e31e4b88af2c";
    public static final Integer ACTIVIDAD_META = 1;
    public static final Integer CALORIAS_META = 2;
    public static final Integer DISTANCIA_TOTAL_META = 3;
    public static final Integer FRECUENCIA_CARDIACA_MAXIMA_META = 4;
    public static final Integer FRECUENCIA_CARDIACA_MEDIA_META = 5;
    public static final Integer RITMO_MEDIO_META = 6;
    
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
    public ResponseEntity<ResponseService> getActivityList(@RequestParam("code") String codeAuth, HttpSession session) {
        ResponseService responseService = new ResponseService();
        try {
            UserDTO userId = (UserDTO)session.getAttribute("user");
            String tokenResponse = getToken(CLIENT_ID, CLIENT_SECRET, codeAuth);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse);
            String token = jo.get("access_token").getAsString();
            String tokenType = jo.get("token_type").getAsString();
            CurlService curlService = new CurlService(tokenType);
            String response = curlService.sendGet("https://www.strava.com/api/v3/athlete/activities", null, true, token);
            String responseActivity = curlService.sendGet("https://www.strava.com/api/v3/activities/768122841", null, true, token);
            responseService.setOutput(responseActivity);
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
    
    private List<UserActivityPerformance> getMetaField(String response, Integer userId) {
        List<UserActivityPerformance> listActivityPerformance = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jarray = (JsonArray) jsonParser.parse(response);
        
        for (JsonElement jsonElement : jarray) {
            JsonObject jo = jarray.getAsJsonObject();
            String activityId = jo.get("id").getAsString();
            String externalId = jo.get("external_id").getAsString();
            String distance = jo.get("distance").getAsString();            
            String startDate = jo.get("start_date_local").getAsString();
            boolean hasHeartrate = jo.get("has_heartrate").getAsBoolean();
            String averageHeartrate = "";
            String maxHeartrate = "";
            
            if(hasHeartrate) {
                averageHeartrate = jo.get("average_heartrate").getAsString();
                maxHeartrate = jo.get("max_heartrate").getAsString();
            }
            
            UserActivityPerformance activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setUserActivityPerformanceId(ACTIVIDAD_META);  
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(activityId);
            listActivityPerformance.add(activityPerformance);
            
            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setUserActivityPerformanceId(DISTANCIA_TOTAL_META);  
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(distance);
            listActivityPerformance.add(activityPerformance);
            
            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setUserActivityPerformanceId(FRECUENCIA_CARDIACA_MAXIMA_META);  
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(maxHeartrate);
            listActivityPerformance.add(activityPerformance);
            
            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setUserActivityPerformanceId(FRECUENCIA_CARDIACA_MEDIA_META);  
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(averageHeartrate);
            listActivityPerformance.add(activityPerformance);
        }
        
        return listActivityPerformance;
    }
}
