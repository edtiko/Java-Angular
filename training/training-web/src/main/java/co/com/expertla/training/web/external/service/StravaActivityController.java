/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.external.service;

import co.com.expertla.base.util.DateUtil;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ActivityPerformanceMetafield;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserActivityPerformance;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.user.UserActivityPerformanceService;
import co.com.expertla.training.service.user.UserService;
import co.com.expertla.training.web.controller.configuration.ActivityController;
import co.com.expertla.training.web.enums.StatusResponse;
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

    public static final String CLIENT_ID = "14512";
    public static final String CLIENT_SECRET = "aa52ba6596961f92a9c03b79b484e31e4b88af2c";
    public static final Integer ACTIVIDAD_META = 1;
    public static final Integer CALORIAS_META = 2;
    public static final Integer DISTANCIA_TOTAL_META = 3;
    public static final Integer FRECUENCIA_CARDIACA_MAXIMA_META = 4;
    public static final Integer FRECUENCIA_CARDIACA_MEDIA_META = 5;
    public static final Integer RITMO_MEDIO_META = 6;
    public static final Integer POTENCIA_MAXIMA_META = 7;
    public static final Integer POTENCIA_MEDIA_META = 8;
    public static final String STRAVA_URL = "https://www.strava.com/";

    @Autowired
    UserService userService;

    @Autowired
    UserActivityPerformanceService activityPerformanceService;

    private String getToken(String clientId, String clientSecret, String code) throws IOException {
        CurlService curlService = new CurlService();
        String param = "?client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code;
        String res = curlService.sendPost(STRAVA_URL + "oauth/token" + param, null, false, null);
        return res;
    }

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

            UserDTO userId = (UserDTO) session.getAttribute("user");
            UserDTO objUser = userService.findById(userId.getUserId());
            objUser.setIndStrava("1");
            objUser.setCodeStrava(codeAuth);
            String tokenResponse = getToken(CLIENT_ID, CLIENT_SECRET, codeAuth);
            JsonParser jsonParser = new JsonParser();
            JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse);
            String token = jo.get("access_token").getAsString();
            String tokenType = jo.get("token_type").getAsString();
            CurlService curlService = new CurlService(tokenType);
            String params = "";

            if (objUser.getLastExecuteStrava() != null) {
                Calendar c = Calendar.getInstance();
                c.setTime(objUser.getLastExecuteStrava());
                long time = c.getTimeInMillis() / 1000L;
                params = "?after=" + time;
            }
            String response = curlService.sendGet(STRAVA_URL + "api/v3/athlete/activities" + params, null, true, token);
            List<UserActivityPerformance> list = getMetaField(curlService, token,
                    response, objUser);

            for (UserActivityPerformance userActivityPerformance : list) {
                activityPerformanceService.create(userActivityPerformance);
            }

            httpResponse.sendRedirect(request.getRequestURL() + "/../../#/data-person");
            return null;
//            responseService.setOutput(response);
//            responseService.setStatus(StatusResponse.SUCCESS.getName());
//            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(StravaActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            httpResponse.sendRedirect(request.getRequestURL() + "/../../#/data-person");
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     *
     * @param curlService
     * @param token
     * @param response
     * @param userId
     * @return
     * @throws IOException
     */
    private List<UserActivityPerformance> getMetaField(CurlService curlService,
            String token,
            String response, UserDTO objUser) throws Exception {
        List<UserActivityPerformance> listActivityPerformance = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jarray = (JsonArray) jsonParser.parse(response);
        Integer userId = objUser.getUserId();
        Date lastExecutionDate = null;

        for (JsonElement jsonElement : jarray) {
            JsonObject jo = jsonElement.getAsJsonObject();
            String activityId = jo.get("id").getAsString();
            String externalId = jo.get("external_id").getAsString();
            String distance = jo.get("distance").getAsString();
            String startDate = jo.get("start_date_local").getAsString();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date executionDate = sp.parse(startDate);
            boolean hasHeartrate = jo.get("has_heartrate").getAsBoolean();
            boolean hasDeviceWatts = jo.get("device_watts").getAsBoolean();
            String averageHeartrate = "";
            String maxHeartrate = "";

            if (hasHeartrate) {
                averageHeartrate = jo.get("average_heartrate").getAsString();
                maxHeartrate = jo.get("max_heartrate").getAsString();
            }

            if (hasDeviceWatts) {
                String averageWatts = jo.get("average_watts").getAsString();
                String maxWatts = jo.get("max_watts").getAsString();

                UserActivityPerformance activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(POTENCIA_MEDIA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(averageWatts.replaceAll("'", ""));
                activityPerformance.setActivityExternalId(activityId);
                activityPerformance.setExecutedDate(executionDate);
                listActivityPerformance.add(activityPerformance);

                activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(POTENCIA_MAXIMA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(maxWatts.replaceAll("'", ""));
                activityPerformance.setActivityExternalId(activityId);
                activityPerformance.setExecutedDate(executionDate);
                listActivityPerformance.add(activityPerformance);
            }

            UserActivityPerformance activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(ACTIVIDAD_META));
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(activityId.replaceAll("'", ""));
            activityPerformance.setActivityExternalId(activityId);
            activityPerformance.setExecutedDate(executionDate);
            listActivityPerformance.add(activityPerformance);

            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(DISTANCIA_TOTAL_META));
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(distance.replaceAll("'", ""));
            activityPerformance.setExecutedDate(executionDate);
            activityPerformance.setActivityExternalId(activityId);
            listActivityPerformance.add(activityPerformance);

            if (maxHeartrate != null && !maxHeartrate.isEmpty()) {
                activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(FRECUENCIA_CARDIACA_MAXIMA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(maxHeartrate.replaceAll("'", ""));
                activityPerformance.setExecutedDate(executionDate);
                activityPerformance.setActivityExternalId(activityId);
                listActivityPerformance.add(activityPerformance);
            }
            
            if (averageHeartrate != null && !averageHeartrate.isEmpty()) {
                activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(FRECUENCIA_CARDIACA_MEDIA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(averageHeartrate.replaceAll("'", ""));
                activityPerformance.setExecutedDate(executionDate);
                activityPerformance.setActivityExternalId(activityId);
                listActivityPerformance.add(activityPerformance);
            }
            
            listActivityPerformance.add(getCaloriesMetaField(curlService, token, activityId, userId));

            if (lastExecutionDate == null || DateUtil.compareDateEqual(executionDate, lastExecutionDate)) {
                lastExecutionDate = sp.parse(startDate);
            }
        }
        if (lastExecutionDate != null) {
            objUser.setLastExecuteStrava(lastExecutionDate);
        }

        userService.updateUser(objUser);

        return listActivityPerformance;
    }

    /**
     *
     * @param curlService
     * @param token
     * @param activityId
     * @param userId
     * @return
     * @throws IOException
     */
    private UserActivityPerformance getCaloriesMetaField(CurlService curlService,
            String token, String activityId, Integer userId) throws IOException {
        String response = curlService.sendGet(STRAVA_URL + "api/v3/activities/" + activityId, null, true, token);
        JsonParser jsonParser = new JsonParser();
        UserActivityPerformance activityPerformance = null;
        JsonObject jo = (JsonObject) jsonParser.parse(response);
        String calories = jo.get("calories").getAsString();
        activityPerformance = new UserActivityPerformance();
        activityPerformance.setUserId(new User(userId));
        activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(CALORIAS_META));
        activityPerformance.setCreationDate(new Date());
        activityPerformance.setActivityExternalId(activityId);
        activityPerformance.setValue(calories.replaceAll("'", ""));

        return activityPerformance;
    }
}
