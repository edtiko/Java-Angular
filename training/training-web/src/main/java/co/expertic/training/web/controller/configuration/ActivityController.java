package co.expertic.training.web.controller.configuration;

import co.expertic.base.util.MessageUtil;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.ActivityCalendarDTO;
import co.expertic.training.model.dto.ActivityDTO;
import co.expertic.training.model.dto.ActivityMovilDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.Activity;
import co.expertic.training.model.entities.UserZone;
import java.util.List;
import co.expertic.training.model.util.ResponseService;
import co.expertic.training.service.configuration.ActivityService;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import co.expertic.training.service.user.UserProfileService;
import co.expertic.training.service.user.UserZoneService;
import co.expertic.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
* Activity Controller <br>
* Info. Creaci贸n: <br>
* fecha 25/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/

@RestController
public class ActivityController {

    @Autowired
    ActivityService activityService; 
    
    @Autowired
    UserProfileService userProfileService;

    @Autowired
    TrainingPlanWorkoutService trainingPlanWorkoutService;

    @Autowired
    UserZoneService userZoneService;

    /**
     * Crea activity <br>
     * Info. Creaci贸n: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivity(@RequestBody Activity activity) {
            ResponseService responseService = new ResponseService();
        try {              
            activity.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            activity.setCreationDate(new Date());
            activityService.create(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activity", "msgRegistroCreado"));
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

    /**
     * Modifica activity <br>
     * Info. Creaci贸n: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateActivity(@RequestBody Activity activity) {
            ResponseService responseService = new ResponseService();
        try {                
            activity.setLastUpdate(new Date());
            activityService.store(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activity", "msgRegistroEditado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al modificar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina activity <br>
     * Info. Creaci贸n: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteActivity(@RequestBody Activity activity) {
            ResponseService responseService = new ResponseService();
        try {           
            activityService.remove(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activity", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    /**
     * Consulta activity <br>
     * Info. Creaci贸n: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     */
    @RequestMapping(value = "/activity/get/all", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> list() {
        ResponseService responseService = new ResponseService();
        try {     
            List<Activity> activityList = activityService.findAllActive();
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Consulta activity paginado <br>
     * Info. Creaci贸n: <br>
     * fecha 25/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/activity/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {   
            paginateDto.setPage( (paginateDto.getPage()-1)*paginateDto.getLimit() );
            List<ActivityDTO> activityList = activityService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(),
                paginateDto.getOrder(), paginateDto.getFilter());
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    
/**
     * Consulta activity por disciplina del usuario <br>
     * Info. Creaci髇: <br>
     * fecha 08/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return
     */
    @RequestMapping(value = "/activity/by/discipline/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listByDisciplineUser(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<ActivityCalendarDTO> activityList = activityService.findByUserDiscipline(userId);
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "create/manual/activity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createManualActivity(@RequestBody ActivityCalendarDTO activity) {
        ResponseService responseService = new ResponseService();
        try {
            Integer id = null;
            boolean exists = activityService.existManualActivity(activity);
            if (exists) {
                responseService.setOutput("El nombre de la actividad ya se encuentra asociada al deporte seleccionado.");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            if (activity.getId() != null) {
                id = activityService.updateManualActivity(activity);
            } else {
                id = activityService.createManualActivity(activity);
            }
            responseService.setOutput(id);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/manual/activity/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> manualActivitylist(@PathVariable("userId") Integer userId) {
        ResponseService responseService = new ResponseService();
        try {
            List<ActivityCalendarDTO> activityList = activityService.findManualActivitiesByUserId(userId);
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    /**
     * Elimina Manual activity <br>
     * Info. Creacionn: <br>
     * fecha Sep 7, 2016 <br>
     *
     * @author Edwin G髆ez
     * @param manualActivityId
     * @return
     */
    @RequestMapping(value = "delete/manual/activity/{manualActivityId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> deleteManualActivity(@PathVariable("manualActivityId") Integer manualActivityId) {
        ResponseService responseService = new ResponseService();
        try {
            activityService.deleteManualActivity(manualActivityId);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.expertic.training.i18n.activity", "msgRegistroEliminado"));
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al eliminar registro");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/manual/activity/id/{trainingPlanWorkoutId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getManualActivity(@PathVariable("trainingPlanWorkoutId") Integer trainingPlanWorkoutId) {
        ResponseService responseService = new ResponseService();
        try {
            ActivityCalendarDTO manualActivity = activityService.findByManualActivityId(trainingPlanWorkoutId);
            responseService.setOutput(manualActivity);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/activity/id/{trainingPlanWorkoutId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getActivity(@PathVariable("trainingPlanWorkoutId") Integer trainingPlanWorkoutId) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingPlanWorkoutDto trainingPlanWorkoutDto = trainingPlanWorkoutService.getPlanWorkoutById(trainingPlanWorkoutId);
            List<UserZone> listUserZone = userZoneService.findByUserId(trainingPlanWorkoutDto.getUserId());
            Integer percentage = trainingPlanWorkoutDto.getPercentageWeather();
            String activity = trainingPlanWorkoutDto.getActivityDescription();

            if (percentage != null && percentage > 0) {
                while (activity.contains("#")) {
                    Pattern p = Pattern.compile("[^#]*#\\s*([0-9]+)");
                    Matcher m = p.matcher(activity);
                    String found = "";
                    while (m.find()) {
                        found = m.group(1);
                    }
                    int indexIni = activity.indexOf("#") + 1;
                    //int indexFin = activity.indexOf(" ", indexIni);
                    if (!"".equals(found)) {
                        try {
                            int time = Integer.parseInt(found);
                            double timePercentage = (time * ((double) percentage / 100));
                            int timeActivity = time - ((int) timePercentage);
                            activity = activity.substring(0, (indexIni - 1)) + timeActivity + activity.substring(activity.lastIndexOf(found) + found.length());
                            //activity = activity.substring(0, (indexIni - 1)) + timeActivity + activity.substring(indexFin);
                        } catch (NumberFormatException n) {
                            activity = activity.replaceAll("#", "");
                        }
                    }

                }
                trainingPlanWorkoutDto.setActivityDescription(activity);
            }

            if (listUserZone != null && !listUserZone.isEmpty()) {
                activity = activity.replaceAll("(?i)zona 2", "en " + listUserZone.get(0).getZoneTwo());
                activity = activity.replaceAll("(?i)zona 3", "en " + listUserZone.get(0).getZoneThree());
                activity = activity.replaceAll("(?i)zona 4", "en " + listUserZone.get(0).getZoneFour());
                activity = activity.replaceAll("(?i)zona 5", "en " + listUserZone.get(0).getZoneFive());
                activity = activity.replaceAll("(?i)zona 6", "en " + listUserZone.get(0).getZoneSix());
                activity = activity.replaceAll("(?i)zona2", "en " + listUserZone.get(0).getZoneTwo());
                activity = activity.replaceAll("(?i)zona3", "en " + listUserZone.get(0).getZoneThree());
                activity = activity.replaceAll("(?i)zona4", "en " + listUserZone.get(0).getZoneFour());
                activity = activity.replaceAll("(?i)zona5", "en " + listUserZone.get(0).getZoneFive());
                activity = activity.replaceAll("(?i)zona6", "en " + listUserZone.get(0).getZoneSix());
                trainingPlanWorkoutDto.setActivityDescription(activity);
            }

            responseService.setOutput(trainingPlanWorkoutDto);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get/activity/id/{trainingPlanWorkoutId}/{ppm}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getActivityPpm(
            @PathVariable("trainingPlanWorkoutId") Integer trainingPlanWorkoutId,
            @PathVariable("ppm") Integer ppm) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingPlanWorkoutDto trainingPlanWorkoutDto = trainingPlanWorkoutService.getPlanWorkoutById(trainingPlanWorkoutId);
            List<UserZone> listUserZone = userZoneService.findByUserId(trainingPlanWorkoutDto.getUserId());
            Integer percentage = trainingPlanWorkoutDto.getPercentageWeather();
            String activity = trainingPlanWorkoutDto.getActivityDescription();

            if (percentage != null && percentage > 0) {
                while (activity.contains("#")) {
                    Pattern p = Pattern.compile("[^#]*#\\s*([0-9]+)");
                    Matcher m = p.matcher(activity);
                    String found = "";
                    while (m.find()) {
                        found = m.group(1);
                    }
                    int indexIni = activity.indexOf("#") + 1;
                    if (!"".equals(found)) {
                        try {
                            int time = Integer.parseInt(found);
                            double timePercentage = (time * ((double) percentage / 100));
                            int timeActivity = time - ((int) timePercentage);
                            activity = activity.substring(0, (indexIni - 1)) + timeActivity + activity.substring(activity.lastIndexOf(found) + found.length());
                            //activity = activity.substring(0, (indexIni - 1)) + timeActivity + activity.substring(indexFin);
                        } catch (NumberFormatException n) {
                            activity = activity.replaceAll("#", "");
                        }
                    }

                }
                trainingPlanWorkoutDto.setActivityDescription(activity);
            }

            if (listUserZone != null && !listUserZone.isEmpty()) {
                for (UserZone userZone : listUserZone) {

                    if (userZone.getZoneType().equals(ppm.toString())) {
                        activity = activity.replaceAll("(?i)zona 2", "en " + userZone.getZoneTwo());
                        activity = activity.replaceAll("(?i)zona 3", "en " + userZone.getZoneThree());
                        activity = activity.replaceAll("(?i)zona 4", "en " + userZone.getZoneFour());
                        activity = activity.replaceAll("(?i)zona 5", "en " + userZone.getZoneFive());
                        activity = activity.replaceAll("(?i)zona 6", "en " + userZone.getZoneSix());
                        activity = activity.replaceAll("(?i)zona2", "en " + userZone.getZoneTwo());
                        activity = activity.replaceAll("(?i)zona3", "en " + userZone.getZoneThree());
                        activity = activity.replaceAll("(?i)zona4", "en " + userZone.getZoneFour());
                        activity = activity.replaceAll("(?i)zona5", "en " + userZone.getZoneFive());
                        activity = activity.replaceAll("(?i)zona6", "en " + userZone.getZoneSix());
                        trainingPlanWorkoutDto.setActivityDescription(activity);
                        break;
                    }
                }

            }

            responseService.setOutput(trainingPlanWorkoutDto);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/get/activity/replace/id/{trainingPlanWorkoutId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getActivityReplace(
            @PathVariable("trainingPlanWorkoutId") Integer trainingPlanWorkoutId) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingPlanWorkoutDto trainingPlanWorkoutDto = trainingPlanWorkoutService.getPlanWorkoutById(trainingPlanWorkoutId);
            Integer activityId = trainingPlanWorkoutDto.getActivityId();
            List<Activity> activityList = activityService.findActivityReplaceByActivity(activityId);
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "create/update/manual/activity/movil", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createManualActivityMovil(@RequestBody ActivityCalendarDTO activity) {
        ResponseService responseService = new ResponseService();
        try {
            Integer id = null;
            boolean exists = activityService.existManualActivity(activity);
            if (exists) {
                responseService.setOutput("El nombre de la actividad ya se encuentra asociada al deporte seleccionado.");
                responseService.setStatus(StatusResponse.FAIL.getName());
                return new ResponseEntity<>(responseService, HttpStatus.OK);
            }
            if (activity.getId() != null) {
                id = activityService.updateManualActivity(activity);
                responseService.setOutput("Actividad creada exitosamente");
            } else {
                id = activityService.createManualActivity(activity);
                responseService.setOutput("Actividad modificada exitosamente");
            }
            
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/activity/default/movil/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> listByDisciplineUserMovil(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        try {
            List<ActivityMovilDTO> activityList = activityService.findActivityDefaultByUserDiscipline(userId);
            
            activityList.stream().forEach((activityMovilDTO) -> {
                String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf("activity"));
                url+= "static/img/";
                activityMovilDTO.setSportIcon(url+activityMovilDTO.getSportIcon());
            });
            
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al consultar activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/get/activity/replace/id/movil/{trainingPlanWorkoutId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> getActivityReplaceMovil(
            @PathVariable("trainingPlanWorkoutId") Integer trainingPlanWorkoutId) {
        ResponseService responseService = new ResponseService();
        try {
            TrainingPlanWorkoutDto trainingPlanWorkoutDto = trainingPlanWorkoutService.getPlanWorkoutById(trainingPlanWorkoutId);
            if(trainingPlanWorkoutDto != null && trainingPlanWorkoutDto.getActivityId() != null){
            Integer activityId = trainingPlanWorkoutDto.getActivityId();
            List<ActivityMovilDTO> activityList = activityService.findActivityReplaceByActivityMovil(activityId);
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
            }else{
            responseService.setDetail("No se encontraron datos");
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);  
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "/get/manual/activity/movil/{userId}", method = RequestMethod.GET)
    public ResponseEntity<ResponseService> manualActivityListMovil(@PathVariable("userId") Integer userId, HttpServletRequest request) {
        ResponseService responseService = new ResponseService();
        try {
            List<ActivityMovilDTO> activityList = activityService.findManualActivitiesMovilByUserId(userId);
            
            activityList.stream().forEach((activityMovilDTO) -> {
                String url = request.getRequestURL().substring(0, request.getRequestURL().indexOf("get/manual"));
                url+= "static/img/";
                activityMovilDTO.setSportIcon(url+activityMovilDTO.getSportIcon());
            });
            
            responseService.setOutput(activityList);
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ActivityController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al traer manual activity");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}
