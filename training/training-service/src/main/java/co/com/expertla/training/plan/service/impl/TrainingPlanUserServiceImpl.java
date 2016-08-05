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
import co.com.expertla.training.plan.service.TrainingPlanUserService;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
* TrainingPlanUserService <br>
* Info. Creación: <br>
* fecha 05/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("trainingPlanUserService")
@Transactional
public class TrainingPlanUserServiceImpl implements TrainingPlanUserService{
     
    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;
    
    
    @Override
    public List<TrainingPlanUser> getPlanWorkoutByUser(Integer userId) throws Exception {
        return trainingPlanUserDao.getPlanWorkoutByUser(userId);
    }
}