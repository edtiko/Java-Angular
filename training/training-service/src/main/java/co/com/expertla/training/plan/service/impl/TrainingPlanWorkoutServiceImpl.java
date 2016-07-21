/**
 * 
 */
package co.com.expertla.training.plan.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.plan.dao.TrainingPlanWorkoutDao;
import co.com.expertla.training.plan.service.TrainingPlanWorkoutService;
import java.util.Calendar;
import java.util.Date;

/**
* TrainingPlanWorkoutService <br>
* Info. Creación: <br>
* fecha 15/07/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("trainingPlanWorkoutService")
@Transactional
public class TrainingPlanWorkoutServiceImpl implements TrainingPlanWorkoutService{
     
    @Autowired
    private TrainingPlanWorkoutDao trainingPlanWorkoutDao;

    @Override
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(User user, Date fromDate, Date toDate) throws Exception {
        
        //TODO:
        
//        GET THE CURRENT DCF FOR THE USER

//        GET THE AVAILABLE DAYS FROM USER PROFILE

          //GET USER AVAILABILITY
          UserAvailability userAvailability = new UserAvailability();
          getAvailableDays(userAvailability, fromDate, toDate);   
//        DAYS_AVAILABLE = FIND THE NEXT AMOUNT OF DAYS AVAILABLE IN THE 30 DAYS 
//                
//        IF(DAYS_AVAILABLE >= DAYS_OBJETIVE){
//          CREATE_PLAN_WITH_AVAILABLE_DAYS()
//        }ELSE{
//          CREATE_PLAN_WITH_EXTRA_DAYS()
//        }
                
        
        return trainingPlanWorkoutDao.getPlanWorkoutByUser(user, fromDate, toDate);
    }
    
    	
    private int getAvailableDays(UserAvailability userAvailability, Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);        

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        
        int availableDays = 0;
        int pivotDay;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            pivotDay = startCal.get(Calendar.DAY_OF_WEEK);
            //Si el dia esta (Los dias en java calendar empiezan el domingo con el 1 y terminan el sabado con el 7)
            if (pivotDay == Calendar.SUNDAY && userAvailability.getSunday()) {
                availableDays++;
            }else if(pivotDay == Calendar.MONDAY && userAvailability.getMonday()){
                availableDays++;
            }else if(pivotDay == Calendar.TUESDAY && userAvailability.getTuesday()){
                availableDays++;
            }else if(pivotDay == Calendar.WEDNESDAY && userAvailability.getWednesday()){
                availableDays++;
            }else if(pivotDay == Calendar.THURSDAY && userAvailability.getThursday()){
                availableDays++;
            }else if(pivotDay == Calendar.FRIDAY && userAvailability.getFriday()){
                availableDays++;
            }else if(pivotDay == Calendar.SATURDAY && userAvailability.getSaturday()) {
                availableDays++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        } 

        return availableDays;
    }

    
}