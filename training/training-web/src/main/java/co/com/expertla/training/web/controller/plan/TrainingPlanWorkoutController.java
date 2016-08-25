package co.com.expertla.training.web.controller.plan;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.model.dto.CalendarEventDto;
import co.com.expertla.training.model.dto.PlanWorkoutDTO;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.util.ResponseService;
import co.com.expertla.training.service.plan.TrainingPlanUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.com.expertla.training.service.plan.TrainingPlanWorkoutService;
import co.com.expertla.training.web.enums.StatusResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
* Servicio Rest para plan de entremaniento <br>
* Info. Creación: <br>
* fecha 15/07/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/  
@RestController
public class TrainingPlanWorkoutController {
  
    @Autowired
    TrainingPlanWorkoutService trainingPlanWorkoutService;  //Service which will do all data retrieval/manipulation work
    
    @Autowired
    TrainingPlanUserService trainingPlanUserService;
  
     
    @RequestMapping(value = "/trainingPlanWorkout/get/planWorkout/by/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CalendarEventDto getPlanWorkoutByUser(@PathVariable("userId") Integer user, @RequestParam("from") long from,
            @RequestParam("to") long to) {
        StringBuilder strResponse = new StringBuilder();
        CalendarEventDto calendarEventDto = new CalendarEventDto();
        try {
            if (user == null) {
                strResponse.append(MessageUtil.getMessageFromBundle("comun", "nullParameters"));
                calendarEventDto.setSuccess(1);
                return calendarEventDto;
            }
            
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(from);
            Date fromDate = calendar.getTime();
            
            calendar.setTimeInMillis(to);
            Date toDate = calendar.getTime();
            
            List<TrainingPlanWorkoutDto> list = trainingPlanWorkoutService.getPlanWorkoutByUser(new User(user), fromDate, toDate);
            
            list.stream().forEach((trainingPlanWorkoutDto) -> {
                trainingPlanWorkoutDto.setStart(trainingPlanWorkoutDto.getWorkoutDate().getTime());
                trainingPlanWorkoutDto.setEnd(trainingPlanWorkoutDto.getWorkoutDate().getTime());
                trainingPlanWorkoutDto.setClassName(trainingPlanWorkoutDto.getSportIcon());
            });
            
            if(list == null) {
                list = new ArrayList();
            }
            
            calendarEventDto.setSuccess(1);
            calendarEventDto.setResult(list);
            return calendarEventDto;
        }   catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
            calendarEventDto.setSuccess(0);
            calendarEventDto.setResult(null);
            return calendarEventDto;
        }
    }
    
    @RequestMapping(value = "trainingPlanWorkout/generate/planWorkout/for/user", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> generatePlanWorkoutByUser(@RequestBody UserProfileDTO userProfile) {
        ResponseService responseService = new ResponseService();
        try {
            
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(new Date());
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            Date startDate = startCal.getTime();
            startCal.add(Calendar.DAY_OF_MONTH, 29);
            Date endDate = startCal.getTime();
            trainingPlanWorkoutService.generatePlan(userProfile.getUserId(), startDate, endDate);
            responseService.setOutput("Plan de Entrenamiento generado satisfactoriamente.");
            responseService.setStatus(StatusResponse.SUCCESS.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }   catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
            responseService.setOutput(e.getMessage());
            responseService.setDetail(e.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "trainingPlanWorkout/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createActivityPlanWorkout(@RequestBody PlanWorkoutDTO planWorkoutDTO) {
            ResponseService responseService = new ResponseService();
        try {           
            List<TrainingPlanUser> listTrainingPlanUser = trainingPlanUserService.getPlanWorkoutByUser(planWorkoutDTO.getUserId());
            
            
            if(listTrainingPlanUser != null && !listTrainingPlanUser.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date activityDate = dateFormat.parse(planWorkoutDTO.getActivityDate());
                responseService.setOutput("actividad creada en el plan exitosamente");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                TrainingPlanWorkout planWorkout = new TrainingPlanWorkout();
                planWorkout.setActivityId(new Activity(planWorkoutDTO.getActivityId()));
                planWorkout.setTrainingPlanUserId(listTrainingPlanUser.get(0));
                planWorkout.setWorkoutDate(activityDate);
                trainingPlanWorkoutService.create(planWorkout);
            }
            
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "trainingPlanWorkout/createPlan", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> createPlanWorkout(@RequestBody PlanWorkoutDTO planWorkoutDTO) {
            ResponseService responseService = new ResponseService();
        try {           
            TrainingPlanWorkout trainingPlanWorkout = new TrainingPlanWorkout();
            trainingPlanWorkout.setTrainingPlanWorkoutId(planWorkoutDTO.getTrainingPlanWorkoutId());
            List<TrainingPlanWorkout> listTrainingPlanUser = trainingPlanWorkoutService.getById(trainingPlanWorkout);
            
            
            if(listTrainingPlanUser != null && !listTrainingPlanUser.isEmpty()) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date activityDate = dateFormat.parse(planWorkoutDTO.getActivityDate());
                responseService.setOutput("actividad creada en el plan exitosamente");
                responseService.setStatus(StatusResponse.SUCCESS.getName());
                TrainingPlanWorkout planWorkout = new TrainingPlanWorkout();
                planWorkout.setActivityId(listTrainingPlanUser.get(0).getActivityId());
                planWorkout.setTrainingPlanUserId(listTrainingPlanUser.get(0).getTrainingPlanUserId());
                planWorkout.setWorkoutDate(activityDate);
                trainingPlanWorkoutService.create(planWorkout);
            }
            
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
    
    @RequestMapping(value = "trainingPlanWorkout/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseService> deletePlanWorkout(@RequestBody TrainingPlanWorkout trainingPlanWorkout) {
            ResponseService responseService = new ResponseService();
        try {           
            trainingPlanWorkoutService.delete(trainingPlanWorkout);
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, ex);
            responseService.setOutput("Error al crear plan");
            responseService.setDetail(ex.getMessage());
            responseService.setStatus(StatusResponse.FAIL.getName());
            return new ResponseEntity<>(responseService, HttpStatus.OK);
        }
    }
}