package co.com.expertla.training.service.impl.user;

import co.com.expertla.base.util.DateUtil;
import co.com.expertla.training.enums.SportEnum;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.ActivityPerformanceMetafield;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.entities.UserActivityPerformance;
import co.com.expertla.training.model.util.CurlService;
import co.com.expertla.training.service.plan.TrainingPlanWorkoutService;
import co.com.expertla.training.service.user.StravaService;
import co.com.expertla.training.service.user.UserActivityPerformanceService;
import co.com.expertla.training.service.user.UserService;
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
import java.util.Objects;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Discipline <br>
 * Creation Date : <br>
 * date 16/11/2016 <br>
 *
 * @author Andres Felipe Lopez
*
 */
@Service
@Transactional
public class StravaServiceImpl implements StravaService {

    @Autowired
    UserService userService;

    @Autowired
    UserActivityPerformanceService activityPerformanceService;
    
   @Autowired
   TrainingPlanWorkoutService trainingPlanWorkoutService;

    @Override
    public void importStravaByUser(Integer userId) throws Exception {
        UserDTO user = userService.findById(userId);
        /*
        Importa datos de strava si el usuario tiene la opcion habilitada y 
        si la fecha de ejecución es menor a la fecha actual
         */
        CurlService curlService = new CurlService();
        String tokenResponse = getToken(curlService, user.getCodeStrava());

        JsonParser jsonParser = new JsonParser();
        JsonObject jo = (JsonObject) jsonParser.parse(tokenResponse);
        String token = jo.get("access_token").getAsString();
        String tokenType = jo.get("token_type").getAsString();
        curlService = new CurlService(tokenType);
        Calendar c = Calendar.getInstance();
        c.setTime(user.getLastExecuteStrava());
        long time = c.getTimeInMillis() / 1000L;
        String params = "?after=" + time;

        String response = curlService.sendGet(StravaService.STRAVA_URL + "api/v3/athlete/activities" + params, null, true, token);
        List<UserActivityPerformance> list = getMetaField(curlService, token, response, user);
        for (UserActivityPerformance userActivityPerformance : list) {
            activityPerformanceService.create(userActivityPerformance);
        }        
    }

    @Override
    public String getToken(CurlService curlService, String code) throws IOException {
        String param = "?client_id=" + CLIENT_ID + "&client_secret=" + CLIENT_SECRET + "&code=" + code;
        String res = curlService.sendPost(STRAVA_URL + "oauth/token" + param, null, false, null);
        return res;
    }

    @Override
    public UserActivityPerformance getCaloriesMetaField(CurlService curlService,
            String token, String activityId, Integer userId) throws IOException {
        String response = curlService.sendGet(StravaService.STRAVA_URL + "api/v3/activities/" + activityId, null, true, token);
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

    @Override
    public List<UserActivityPerformance> getMetaField(CurlService curlService,
            String token,
            String response, UserDTO objUser) throws Exception {
        List<UserActivityPerformance> listActivityPerformance = new ArrayList<>();
        JsonParser jsonParser = new JsonParser();
        JsonArray jarray = (JsonArray) jsonParser.parse(response);
        Integer userId = objUser.getUserId();
        Date lastExecutionDate = null;
        List<TrainingPlanWorkoutDto> activitiesPlan = trainingPlanWorkoutService.getPlanWorkoutByUser(new User(objUser.getUserId()), objUser.getLastExecuteStrava(), new Date());
        List<TrainingPlanWorkoutDto> result = new ArrayList<>();
        for (JsonElement jsonElement : jarray) {
            JsonObject jo = jsonElement.getAsJsonObject();
            String activityId = jo.get("id").getAsString();
            String externalId = jo.get("external_id").getAsString();
            String distance = jo.get("distance").getAsString();
            String elapsedTime = jo.get("elapsed_time").getAsString();
            String startDate = jo.get("start_date_local").getAsString();
            String type = jo.get("type").getAsString();
            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            Date executionDate = sp.parse(startDate);
            boolean hasHeartrate = jo.get("has_heartrate").getAsBoolean();
            boolean hasDeviceWatts = jo.get("device_watts").getAsBoolean();
            String averageHeartrate = "";
            String maxHeartrate = "";

            activitiesPlan.stream().filter((dto) -> (dto.getWorkoutDate() == executionDate && Objects.equals(dto.getSportId(), SportEnum.valueOf(type.toUpperCase()).getId()))).forEach((TrainingPlanWorkoutDto dto) -> {
                dto.setExecutedTime(Double.parseDouble(elapsedTime.replaceAll("'", "")));
                dto.setExecutedDistance(Double.parseDouble(distance.replaceAll("'", "")));
                try {
                    trainingPlanWorkoutService.update(dto);
                } catch (Exception ex) {
                    Logger.getLogger(StravaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

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
                activityPerformance.setType(type);
                listActivityPerformance.add(activityPerformance);

                activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(POTENCIA_MAXIMA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(maxWatts.replaceAll("'", ""));
                activityPerformance.setActivityExternalId(activityId);
                activityPerformance.setExecutedDate(executionDate);
                activityPerformance.setType(type);
                listActivityPerformance.add(activityPerformance);
            }

            UserActivityPerformance activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(ACTIVIDAD_META));
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(activityId.replaceAll("'", ""));
            activityPerformance.setActivityExternalId(activityId);
            activityPerformance.setExecutedDate(executionDate);
            activityPerformance.setType(type);
            listActivityPerformance.add(activityPerformance);

            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(DISTANCIA_TOTAL_META));
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(distance.replaceAll("'", ""));
            activityPerformance.setExecutedDate(executionDate);
            activityPerformance.setActivityExternalId(activityId);
            activityPerformance.setType(type);
            listActivityPerformance.add(activityPerformance);
            
            activityPerformance = new UserActivityPerformance();
            activityPerformance.setUserId(new User(userId));
            activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(RITMO_MEDIO_META));
            activityPerformance.setCreationDate(new Date());
            activityPerformance.setValue(elapsedTime.replaceAll("'", ""));
            activityPerformance.setExecutedDate(executionDate);
            activityPerformance.setActivityExternalId(activityId);
            activityPerformance.setType(type);
            listActivityPerformance.add(activityPerformance);

            if (maxHeartrate != null && !maxHeartrate.isEmpty()) {
                activityPerformance = new UserActivityPerformance();
                activityPerformance.setUserId(new User(userId));
                activityPerformance.setActivityPerformanceMetafieldId(new ActivityPerformanceMetafield(FRECUENCIA_CARDIACA_MAXIMA_META));
                activityPerformance.setCreationDate(new Date());
                activityPerformance.setValue(maxHeartrate.replaceAll("'", ""));
                activityPerformance.setExecutedDate(executionDate);
                activityPerformance.setActivityExternalId(activityId);
                activityPerformance.setType(type);
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
                activityPerformance.setType(type);
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
}
