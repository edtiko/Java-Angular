package co.com.expertla.training.web.controller.configuration;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.enums.Status;
import co.com.expertla.training.model.dto.ActivityCalendarDTO;
import co.com.expertla.training.model.dto.ActivityDTO;
import co.com.expertla.training.model.dto.PaginateDto;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.model.entities.UserZone;
import java.util.List;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.configuration.ActivityService;
import co.com.expertla.training.service.plan.TrainingPlanWorkoutService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.util.Date;
import java.util.logging.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.com.expertla.training.service.user.UserProfileService;
import co.com.expertla.training.service.user.UserZoneService;

/**
 * Activity Controller <br>
 * Info. Creaci贸n: <br>
 * fecha 5/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
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
     * Info. Creaci髇: <br>
     * fecha 5/08/2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivity(@RequestBody Activity activity) {
        ResponseService responseService = new ResponseService();
        try {
//            Activity activityName = new Activity();
//            activityName.setName(activity.getName());
//            List<Activity> listActivityName = activityService.findByFiltro(activityName);

//            if(listActivityName != null && !listActivityName.isEmpty()) {
//                responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgNombreExiste"));
//                responseService.setStatus(StatusResponse.FAIL.getName());
//                return new ResponseEntity<>(responseService, HttpStatus.OK);
//            }
            activity.setStateId(Short.valueOf(Status.ACTIVE.getId()));
            activity.setCreationDate(new Date());
            activityService.create(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgRegistroCreado"));
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
     * fecha Sep 2, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/update", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> updateActivity(@RequestBody Activity activity) {
        ResponseService responseService = new ResponseService();
        try {
//            Activity activityName = new Activity();
//            activityName.setName(activity.getName());
//            List<Activity> listActivityName = activityService.findByFiltro(activityName);
//            
//            if(listActivityName != null && !listActivityName.isEmpty()) {
//                boolean existName = false;
//                for (Activity activity1 : listActivityName) {
//                    if (!activity1.getActivityId().equals(activity.getActivityId())) {
//                        existName = true;
//                    }
//                }
//
//                if (existName) {
//                    responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgNombreExiste"));
//                    responseService.setStatus(StatusResponse.FAIL.getName());
//                    return new ResponseEntity<>(responseService, HttpStatus.OK);
//                }                
//            }

            activity.setLastUpdate(new Date());
            activityService.store(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgRegistroEditado"));
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
     * fecha Sep 2, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param activity
     * @return
     */
    @RequestMapping(value = "activity/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deleteActivity(@RequestBody Activity activity) {
        ResponseService responseService = new ResponseService();
        try {
            activityService.remove(activity);
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgRegistroEliminado"));
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
     * Info. Creaci髇: <br>
     * fecha 5/08/2016 <br>
     *
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
     * fecha Sep 2, 2016 <br>
     *
     * @author Andres Felipe Lopez Rodriguez
     * @param paginateDto
     * @return
     */
    @RequestMapping(value = "/activity/paginated", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> listPaginated(@RequestBody PaginateDto paginateDto) {
        ResponseService responseService = new ResponseService();
        try {
            paginateDto.setPage((paginateDto.getPage() - 1) * paginateDto.getLimit());
            List<ActivityDTO> activityList = activityService.findPaginate(paginateDto.getPage(), paginateDto.getLimit(), paginateDto.getOrder());
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
            responseService.setOutput(MessageUtil.getMessageFromBundle("co.com.expertla.training.i18n.activity", "msgRegistroEliminado"));
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
                    int indexIni = activity.indexOf("#") + 1;
                    int indexFin = activity.indexOf(" ", indexIni);
                    try {
                        int time = Integer.parseInt(activity.substring(indexIni, indexFin));
                        double timePercentage = (time * ((double) percentage / 100));
                        int timeActivity = time - ((int) timePercentage);
                        activity = activity.substring(0, (indexIni - 1)) + timeActivity + activity.substring(indexFin);
                    } catch (NumberFormatException n) {
                        activity = activity.replaceAll("#", "");
                    }

                }
                trainingPlanWorkoutDto.setActivityDescription(activity);
            }

            if (listUserZone != null && !listUserZone.isEmpty()) {
                activity = activity.replaceAll("zona 2", "en " + listUserZone.get(0).getZoneTwo());
                activity = activity.replaceAll("zona 3", "en " + listUserZone.get(0).getZoneThree());
                activity = activity.replaceAll("zona 4", "en " + listUserZone.get(0).getZoneFour());
                activity = activity.replaceAll("zona 5", "en " + listUserZone.get(0).getZoneFive());
                activity = activity.replaceAll("zona 6", "en " + listUserZone.get(0).getZoneSix());
                activity = activity.replaceAll("zona2", "en " + listUserZone.get(0).getZoneTwo());
                activity = activity.replaceAll("zona3", "en " + listUserZone.get(0).getZoneThree());
                activity = activity.replaceAll("zona4", "en " + listUserZone.get(0).getZoneFour());
                activity = activity.replaceAll("zona5", "en " + listUserZone.get(0).getZoneFive());
                activity = activity.replaceAll("zona6", "en " + listUserZone.get(0).getZoneSix());
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
}
