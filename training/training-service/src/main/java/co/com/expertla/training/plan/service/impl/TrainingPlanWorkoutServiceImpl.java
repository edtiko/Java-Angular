package co.com.expertla.training.plan.service.impl;

import co.com.expertla.training.configuration.dao.ActivityDao;
import co.com.expertla.training.plan.dao.DcfDao;
import co.com.expertla.training.user.dao.UserAvailabilityDao;
import co.com.expertla.training.user.dao.UserProfileDao;
import co.com.expertla.training.enums.StateEnum;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.entities.Dcf;
import co.com.expertla.training.model.entities.State;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.plan.dao.TrainingPlanDao;
import co.com.expertla.training.plan.dao.TrainingPlanUserDao;
import co.com.expertla.training.plan.dao.TrainingPlanWorkoutDao;
import co.com.expertla.training.plan.service.TrainingPlanWorkoutService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

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
    
    @Autowired
    private UserProfileDao userProfileDao;
    
    @Autowired
    private DcfDao dcfDao;
    
    @Autowired
    private UserAvailabilityDao userAvailabilityDao;
    
    @Autowired
    private ActivityDao activityDao;
    
    @Autowired
    private TrainingPlanDao trainingPlanDao;
    
    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;
    
    @Override
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(User user, Date fromDate, Date toDate) throws Exception {
        return trainingPlanWorkoutDao.getPlanWorkoutByUser(user, fromDate, toDate);
    }
    
    @Override
    public void generatePlan(Integer id,Date fromDate, Date toDate) throws Exception {
        UserProfile userProfile = userProfileDao.findByUserId(id);
        Dcf dcf = dcfDao.findByObjectiveIdAndModalityId(userProfile.getObjectiveId().getObjectiveId(), userProfile.getModalityId().getModalityId());
        List<UserAvailability> userAvailabilityList = userAvailabilityDao.findByUserId(id);
        UserAvailability userAvailability =(userAvailabilityList == null || userAvailabilityList.isEmpty()) ? null : userAvailabilityList.get(0);
        int daysAvailable = getAvailableDays(userAvailability, fromDate, toDate);
        
        if(daysAvailable >= dcf.getSessions()) {
            extraDays(userAvailability, fromDate, toDate, userProfile, dcf,0);
        } else {
            extraDays(userAvailability, fromDate, toDate, userProfile, dcf,(dcf.getSessions() - daysAvailable));
        }
        
    }
    
    @Override
    public TrainingPlanWorkout create(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.createTrainingPlanWorkout(trainingPlanWorkout);
    }
    
    @Override
    public void delete(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        trainingPlanWorkoutDao.remove(trainingPlanWorkout, trainingPlanWorkout.getTrainingPlanWorkoutId());
    }
    	
    private void exactDays(UserAvailability userAvailability, Date startDate, Date endDate, UserProfile userProfile, Dcf dcf) throws Exception {
        List<Activity> activityList = activityDao.findByObjectiveIdAndModalityId(userProfile.getObjectiveId().getObjectiveId(), 
                userProfile.getModalityId().getModalityId());
        List<TrainingPlanWorkout> workouts = new ArrayList<TrainingPlanWorkout>();
        String pattern = dcf.getPattern();
        //Start date
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);  
        //End date
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        String[] parts = pattern.split("-");
        int length = parts.length;   
        String code = "";
        
        List<Activity> list = new ArrayList<>(activityList.size());
        for(Activity act: activityList) list.add(act);
        
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setName(userProfile.getObjectiveId().getName()+"-"+userProfile.getModalityId().getName()+"-"+userProfile.getUserProfileId());
        trainingPlan.setCreationDate(startDate);
        trainingPlan.setEndDate(endDate);
        
        TrainingPlanUser planUser = new TrainingPlanUser();
        planUser.setTrainingPlanId(trainingPlan);
        planUser.setUserId(userProfile.getUserId());
        planUser.setStateId(new State(StateEnum.ACTIVE.getId()));
        
        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        int pivotDay;
        Activity activity =  new Activity();
        int i = 0;
        boolean planWorkout =false;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            if(i<length) {
                code = parts[i];
            } else {
                i = 0;
                code = parts[i];
            }
            workout = new TrainingPlanWorkout();
            activity = getActivityByPC(list,activityList, code, false);
            pivotDay = startCal.get(Calendar.DAY_OF_WEEK);
            if (pivotDay == Calendar.SUNDAY && userAvailability.getSunday()) {
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            }else if(pivotDay == Calendar.MONDAY && userAvailability.getMonday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.TUESDAY && userAvailability.getTuesday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.WEDNESDAY && userAvailability.getWednesday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.THURSDAY && userAvailability.getThursday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.FRIDAY && userAvailability.getFriday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.SATURDAY && userAvailability.getSaturday()) {
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            }
            if(planWorkout) {
                list.remove(activity);
                planWorkout = false;
                i++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        } 
        trainingPlanDao.create(trainingPlan);
        trainingPlanUserDao.create(planUser);
        trainingPlanWorkoutDao.createList(workouts);
    }
    
     private void extraDays(UserAvailability userAvailability, Date startDate, Date endDate, UserProfile userProfile, Dcf dcf, Integer sessionsLeft) throws Exception {
        List<Activity> activityList = activityDao.findByObjectiveIdAndModalityId(userProfile.getObjectiveId().getObjectiveId(), 
                userProfile.getModalityId().getModalityId());
        List<TrainingPlanWorkout> workouts = new ArrayList<TrainingPlanWorkout>();
        String pattern = dcf.getPattern();
        //Start date
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);  
        //End date
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        String[] parts = pattern.split("-");
        int length = parts.length;   
        String code = "";
        
        List<Activity> list = new ArrayList<>(activityList.size());
        activityList.stream().forEach((act) -> {
            list.add(act);
        });
        
        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setName(userProfile.getObjectiveId().getName()+"-"+userProfile.getModalityId().getName()+"-"+userProfile.getUserProfileId());
        trainingPlan.setCreationDate(startDate);
        trainingPlan.setEndDate(endDate);
        
        TrainingPlanUser planUser = new TrainingPlanUser();
        planUser.setTrainingPlanId(trainingPlan);
        planUser.setUserId(userProfile.getUserId());
        planUser.setStateId(new State(StateEnum.ACTIVE.getId()));
        
        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        int pivotDay;
        Activity activity =  new Activity();
        int i = 0;
        boolean planWorkout =false;
        boolean originalList = false;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            if(i<length) {
                code = parts[i];
            } else {
                i = 0;
                code = parts[i];
            }
            workout = new TrainingPlanWorkout();
            activity = getActivityByPC(list,activityList, code, originalList);
            pivotDay = startCal.get(Calendar.DAY_OF_WEEK);
            if (pivotDay == Calendar.SUNDAY && (userAvailability.getSunday() || sessionsLeft > 0)) {
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
                sessionsLeft--;
            }else if(pivotDay == Calendar.MONDAY && userAvailability.getMonday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.TUESDAY && (userAvailability.getTuesday() || sessionsLeft > 0)){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
                sessionsLeft--;
            } else if(pivotDay == Calendar.WEDNESDAY && userAvailability.getWednesday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.THURSDAY && (userAvailability.getThursday() || sessionsLeft > 0)){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
                sessionsLeft--;
            } else if(pivotDay == Calendar.FRIDAY && userAvailability.getFriday()){
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
            } else if(pivotDay == Calendar.SATURDAY && (userAvailability.getSaturday() || sessionsLeft > 0)) {
                workout.setTrainingPlanId(trainingPlan);
                workout.setActivityId(activity);
                workout.setWorkoutDate(startCal.getTime());
                workouts.add(workout);
                planWorkout = true;
                sessionsLeft--;
            }
            if(planWorkout) {
                list.remove(activity);
                planWorkout = false;
                i++;
            }
            if(originalList) {
                activityList.remove(activity);
                originalList = false;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        } 
        trainingPlanDao.create(trainingPlan);
        trainingPlanUserDao.create(planUser);
        trainingPlanWorkoutDao.createList(workouts);
    }
    
    private Activity getActivityByPC(List<Activity> activityList,List<Activity> originalList, String pattern, boolean original) {
        Activity act = new Activity();
        for (Activity activity : activityList) {
            if(activity.getPhysiologicalCapacityId().getCode().equals(pattern)) {
                act = activity;
                break;
            }
        }
        if (act.getActivityId() == null) {
            for (Activity activity : originalList) {
                if (activity.getPhysiologicalCapacityId().getCode().equals(pattern)) {
                    act = activity;
                    original = true;
                    break;
                }
            }
        }
        return act;
    }
    
    /**
     * Gets the quantity of days available in a range date according to user availability
     * @param userAvailability
     * @param startDate
     * @
     * param endDate
     * @return 
     */
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

    @Override
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.getById(trainingPlanWorkout);
    }

    
}