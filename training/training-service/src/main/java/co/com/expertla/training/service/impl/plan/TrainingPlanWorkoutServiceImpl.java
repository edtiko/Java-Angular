package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.configuration.dao.ActivityDao;
import co.com.expertla.training.dao.plan.DcfDao;
import co.com.expertla.training.dao.user.UserAvailabilityDao;
import co.com.expertla.training.dao.user.UserProfileDao;
import co.com.expertla.training.enums.StateEnum;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.Activity;
import co.com.expertla.training.model.entities.Dcf;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.UserAvailability;
import co.com.expertla.training.model.entities.UserProfile;
import co.com.expertla.training.dao.plan.TrainingPlanDao;
import co.com.expertla.training.dao.plan.TrainingPlanUserDao;
import co.com.expertla.training.dao.plan.TrainingPlanWorkoutDao;
import co.com.expertla.training.service.plan.TrainingPlanWorkoutService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * TrainingPlanWorkoutService <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 * @author Andres Felipe Lopez Rodriguez
 */
@Service("trainingPlanWorkoutService")
@Transactional
public class TrainingPlanWorkoutServiceImpl implements TrainingPlanWorkoutService {

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
    public void generatePlan(Integer id, Date fromDate, Date toDate) throws Exception {
        UserProfile userProfile = userProfileDao.findByUserId(id);
        Dcf dcf = null;
        if(userProfile.getModalityId() != null && userProfile.getObjectiveId() != null) {
            dcf = dcfDao.findByObjectiveIdAndModalityId(userProfile.getObjectiveId().getObjectiveId(), userProfile.getModalityId().getModalityId());
        }
        
        
        if(dcf == null) {
            throw new Exception("No se puede generar plan, no existe configuración para el objetivo y la modalidad seleccionada");
        }
        
        List<UserAvailability> userAvailabilityList = userAvailabilityDao.findByUserId(id);
        UserAvailability userAvailability = (userAvailabilityList == null || userAvailabilityList.isEmpty()) ? null : userAvailabilityList.get(0);
        UserAvailability modifiedAvailability = new UserAvailability();
        int daysAvailable = getAvailableDays(userAvailability, fromDate, toDate);
        if (daysAvailable == dcf.getSessions()) {
            exactDays(userAvailability, fromDate, toDate, userProfile, dcf);
        } else if (daysAvailable > dcf.getSessions()) {
            arrangeUserAvailability(userAvailability,modifiedAvailability, dcf.getSessions());
            daysAvailable = getAvailableDays(modifiedAvailability, fromDate, toDate);
            extraDays(modifiedAvailability, fromDate, toDate, userProfile, dcf, (dcf.getSessions() - daysAvailable));
        } else {
            arrangeUserAvailabilityWithMoreDays(userAvailability,modifiedAvailability, dcf.getSessions());
            daysAvailable = getAvailableDays(modifiedAvailability, fromDate, toDate);
            extraDays(modifiedAvailability, fromDate, toDate, userProfile, dcf, (dcf.getSessions() - daysAvailable));
        }

    }

    /**
     * Reparte la disponibilidad del usuario cuando tiene muchos días disponibles
     * @param userAvailability
     * @param modifiedAvailability
     * @param sessions
     * @return 
     */
    private UserAvailability arrangeUserAvailability(UserAvailability userAvailability,UserAvailability modifiedAvailability, Integer sessions) {
        //number of days needed per week to achieve the goal
        int daysPerWeek = Math.floorDiv(sessions, 4);
        boolean dayBefore = false;
        UserAvailability backupAvailability = new UserAvailability();
        if (userAvailability.getSunday()) {
            modifiedAvailability.setSunday(true);
            daysPerWeek--;
            dayBefore = true;
        } else {
            backupAvailability.setSunday(true);
        }
        if (userAvailability.getMonday() && daysPerWeek > 0) {
            if (!dayBefore) {
                modifiedAvailability.setMonday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setMonday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setMonday(true);
            dayBefore = false;  
        }
        
        if (userAvailability.getTuesday() && daysPerWeek > 0) {
            if (!dayBefore) {
                modifiedAvailability.setTuesday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setTuesday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setTuesday(true);
            dayBefore = false;
        }
        
        if (userAvailability.getWednesday() && daysPerWeek > 0) {
            if (!dayBefore) {
                modifiedAvailability.setWednesday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setWednesday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setWednesday(true);
            dayBefore = false;
        }
        
        if (userAvailability.getThursday() && daysPerWeek > 0) {
            if (!dayBefore) {
                modifiedAvailability.setThursday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setThursday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setThursday(true);
            dayBefore = false;
        }
        
        if (userAvailability.getFriday() && daysPerWeek > 0) {
            if (dayBefore) {
                modifiedAvailability.setFriday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setFriday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setFriday(true);
            dayBefore = false;
        }
        
        if (userAvailability.getSaturday() && daysPerWeek > 0) {
            if (!dayBefore) {
                modifiedAvailability.setSaturday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setSaturday(true);
                dayBefore = false;
            }
        } else {
            backupAvailability.setSaturday(true);
            dayBefore = false;
        }
        
        return backupAvailability;
    }

    /**
     * Reparte la disponibilidad del usuario cuando los días son muy pocos para cumplir el objetivo
     * @param userAvailability
     * @param modifiedAvailability
     * @param sessions
     * @return 
     */
    private UserAvailability arrangeUserAvailabilityWithMoreDays(UserAvailability userAvailability,UserAvailability modifiedAvailability, Integer sessions) {
        //number of days needed per week to achieve the goal
        int daysPerWeek = Math.floorDiv(sessions, 4);
        boolean dayBefore = false;
        UserAvailability backupAvailability = new UserAvailability();
        List<Integer> daysArray = new ArrayList<>();
        daysArray.add(userAvailability.getSunday() ? 1 : 0);
        daysArray.add(userAvailability.getMonday()? 1 : 0);
        daysArray.add(userAvailability.getTuesday()? 1 : 0);
        daysArray.add(userAvailability.getWednesday()? 1 : 0);
        daysArray.add(userAvailability.getThursday()? 1 : 0);
        daysArray.add(userAvailability.getFriday()? 1 : 0);
        daysArray.add(userAvailability.getSaturday()? 1 : 0);
        
        for (int i = 0; i < 7; i++) {
            if(daysArray.get(i) == 1) {
                switch (i) {
                    case 0:
                        modifiedAvailability.setSunday(true);
                        daysPerWeek--;
                        break;
                    case 1:
                        modifiedAvailability.setMonday(true);
                        daysPerWeek--;
                        break;
                    case 2:
                        modifiedAvailability.setTuesday(true);
                        daysPerWeek--;
                        break;
                    case 3:
                        modifiedAvailability.setWednesday(true);
                        daysPerWeek--;
                        break;
                    case 4:
                        modifiedAvailability.setThursday(true);
                        daysPerWeek--;
                        break;
                    case 5:
                        modifiedAvailability.setFriday(true);
                        daysPerWeek--;
                        break;
                    case 6:
                        modifiedAvailability.setSaturday(true);
                        daysPerWeek--;
                        break;
                }
            }
        }
        
         //Sunday
        if (modifiedAvailability.getSunday()) {
            dayBefore = true;
        } else {
            backupAvailability.setSunday(true);
        }
        
        //Monday
        if(daysPerWeek > 0) {
            if (modifiedAvailability.getMonday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setMonday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setMonday(true);
                dayBefore = false;
            }
        }
        
        //Tuesday
        if(daysPerWeek >0) {
            if (modifiedAvailability.getTuesday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setTuesday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setTuesday(true);
                dayBefore = false;
            }
        }
        
        //Wednesday
        if(daysPerWeek >0) {
            if (modifiedAvailability.getWednesday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setWednesday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setWednesday(true);
                dayBefore = false;
            }
        }
        
        //Thursday
        if(daysPerWeek >0) {
            if (modifiedAvailability.getThursday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setThursday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setThursday(true);
                dayBefore = false;
            }
        }

        //Friday
        if(daysPerWeek >0) {
            if (modifiedAvailability.getFriday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setFriday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setFriday(true);
                dayBefore = false;
            }
        }

        //Saturday
        if(daysPerWeek > 0) {
            if (modifiedAvailability.getSaturday()) {
                dayBefore = true;
            } else if (!dayBefore) {
                modifiedAvailability.setSaturday(true);
                daysPerWeek--;
                dayBefore = true;
            } else {
                backupAvailability.setSaturday(true);
                dayBefore = false;
            }
        }
                
        return backupAvailability;
    }
    
    /**
     * Asigna las actividades a los dias de la disponibilidad del usuario, cuando los dias disponibles cumplen con el numero de sesiones
     * @param userAvailability
     * @param startDate
     * @param endDate
     * @param userProfile
     * @param dcf
     * @throws Exception 
     */
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
        for (Activity act : activityList) {
            list.add(act);
        }

        TrainingPlan trainingPlan = new TrainingPlan();
        trainingPlan.setName(userProfile.getObjectiveId().getName() + "-" + userProfile.getModalityId().getName() + "-" + userProfile.getUserProfileId());
        trainingPlan.setCreationDate(startDate);
        trainingPlan.setEndDate(endDate);

        TrainingPlanUser planUser = new TrainingPlanUser();
        planUser.setTrainingPlanId(trainingPlan);
        planUser.setUserId(userProfile.getUserId());
        planUser.setStateId(StateEnum.ACTIVE.getId());

        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        int pivotDay;
        Activity activity = new Activity();
        int i = 0;
        boolean planWorkout = false;
        Boolean originalList = false;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            if (i < length) {
                code = parts[i];
            } else {
                i = 0;
                code = parts[i];
            }
            activity = getActivityByPC(list, activityList, code, originalList);
            workout = new TrainingPlanWorkout();
            workout.setTrainingPlanUserId(planUser);
            workout.setActivityId(activity);
            workout.setWorkoutDate(startCal.getTime());
            pivotDay = startCal.get(Calendar.DAY_OF_WEEK);
            if (pivotDay == Calendar.SUNDAY && userAvailability.getSunday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.MONDAY && userAvailability.getMonday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.TUESDAY && userAvailability.getTuesday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.WEDNESDAY && userAvailability.getWednesday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.THURSDAY && userAvailability.getThursday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.FRIDAY && userAvailability.getFriday()) {
                workouts.add(workout);
                planWorkout = true;
            } else if (pivotDay == Calendar.SATURDAY && userAvailability.getSaturday()) {
                workouts.add(workout);
                planWorkout = true;
            }
            if (planWorkout) {
                list.remove(activity);
                planWorkout = false;
                i++;
            }
            if (originalList) {
                activityList.remove(activity);
                originalList = false;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        trainingPlanDao.create(trainingPlan);
        trainingPlanUserDao.create(planUser);
        trainingPlanWorkoutDao.createList(workouts);
    }
    
     private void extraDays(UserAvailability userAvailability, Date startDate, Date endDate, UserProfile userProfile, Dcf dcf,
            Integer sessionsLeft) throws Exception {
        List<Activity> activityList = activityDao.findByObjectiveIdAndModalityId(userProfile.getObjectiveId().getObjectiveId(),
                userProfile.getModalityId().getModalityId());
        List<TrainingPlanWorkout> workouts = new ArrayList<TrainingPlanWorkout>();
        String pattern = dcf.getPattern();
        int sessions = dcf.getSessions();
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
        trainingPlan.setName(userProfile.getObjectiveId().getName() + "-" + userProfile.getModalityId().getName() + "-" + userProfile.getUserProfileId());
        trainingPlan.setCreationDate(startDate);
        trainingPlan.setEndDate(endDate);

        TrainingPlanUser planUser = new TrainingPlanUser();
        planUser.setTrainingPlanId(trainingPlan);
        planUser.setUserId(new User(userProfile.getUserId().getUserId()));
        planUser.setStateId(StateEnum.ACTIVE.getId());

        TrainingPlanWorkout workout = new TrainingPlanWorkout();
        int pivotDay;
        Activity activity = new Activity();
        int i = 0;
        boolean planWorkout = false;
        boolean dayUsed = false;
        Boolean originalList = false;
        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            if (i < length) {
                code = parts[i];
            } else {
                i = 0;
                code = parts[i];
            }    
            activity = getActivityByPC(list, activityList, code, originalList);
            workout = new TrainingPlanWorkout();
            workout.setTrainingPlanUserId(planUser);
            workout.setActivityId(activity);
            workout.setWorkoutDate(startCal.getTime());
            pivotDay = startCal.get(Calendar.DAY_OF_WEEK);
            if (sessions > 0) {

                switch (pivotDay) {
                    case Calendar.SUNDAY:
                        if (userAvailability.getSunday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.MONDAY:
                        if (userAvailability.getMonday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.TUESDAY:
                        if (userAvailability.getTuesday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.WEDNESDAY:
                        if (userAvailability.getWednesday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.THURSDAY:
                        if (userAvailability.getThursday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.FRIDAY:
                        if (userAvailability.getFriday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false;
                        }   break;
                    case Calendar.SATURDAY:
                        if (userAvailability.getSaturday()) {
                            workouts.add(workout);
                            planWorkout = true;
                        } else if(sessionsLeft > 0 && !dayUsed) {
                            dayUsed = true;
                            sessionsLeft--;
                            workouts.add(workout);
                            planWorkout = true;
                        } else {
                            dayUsed = false; 
                        }   break;
                    default:
                        break;
                }
            }
            if (planWorkout) {
                list.remove(activity);
                planWorkout = false;
                sessions--;
                i++;
            }
            if (originalList) {
                activityList.remove(activity);
                originalList = false;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        trainingPlanDao.create(trainingPlan);
        trainingPlanUserDao.create(planUser);
        trainingPlanWorkoutDao.createList(workouts);
    }

    private Activity getActivityByPC(List<Activity> activityList, List<Activity> originalList, String pattern, Boolean original) {
        Activity act = new Activity();
        for (Activity activity : activityList) {
            if (activity.getPhysiologicalCapacityId().getCode().equals(pattern)) {
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
     * Gets the quantity of days available in a range date according to user
     * availability
     *
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
            } else if (pivotDay == Calendar.MONDAY && userAvailability.getMonday()) {
                availableDays++;
            } else if (pivotDay == Calendar.TUESDAY && userAvailability.getTuesday()) {
                availableDays++;
            } else if (pivotDay == Calendar.WEDNESDAY && userAvailability.getWednesday()) {
                availableDays++;
            } else if (pivotDay == Calendar.THURSDAY && userAvailability.getThursday()) {
                availableDays++;
            } else if (pivotDay == Calendar.FRIDAY && userAvailability.getFriday()) {
                availableDays++;
            } else if (pivotDay == Calendar.SATURDAY && userAvailability.getSaturday()) {
                availableDays++;
            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }
        return availableDays;
    }

	@Override
    public TrainingPlanWorkout create(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.createTrainingPlanWorkout(trainingPlanWorkout);
    }
    
    @Override
    public void delete(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        trainingPlanWorkoutDao.remove(trainingPlanWorkout, trainingPlanWorkout.getTrainingPlanWorkoutId());
    }

	@Override
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return trainingPlanWorkoutDao.getById(trainingPlanWorkout);
    }

}