/**
 * 
 */
package co.com.expertla.training.web.plan.controller;

import co.com.expertla.base.util.MessageUtil;
import co.com.expertla.training.model.dto.CalendarEventDto;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.dto.UserProfileDTO;
import co.com.expertla.training.model.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import co.com.expertla.training.plan.service.TrainingPlanWorkoutService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                trainingPlanWorkoutDto.setClassName("event-" + trainingPlanWorkoutDto.getDiscipline().toLowerCase());
            });
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
    public void generatePlanWorkoutByUser(@RequestBody UserProfileDTO userProfile) {
        StringBuilder strResponse = new StringBuilder();
        try {
            
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(new Date());
            Date startDate = startCal.getTime();
            startCal.add(Calendar.DAY_OF_MONTH, 30);
            Date endDate = startCal.getTime();
            trainingPlanWorkoutService.generatePlan(userProfile.getUserId(), startDate, endDate);
        }   catch (Exception e) {
            Logger.getLogger(TrainingPlanWorkoutController.class.getName()).log(Level.SEVERE, null, e);
//            return calendarEventDto;
        }
    }
}