package co.com.expertla.training.plan.service.impl;

import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.plan.dao.TrainingPlanUserDao;
import co.com.expertla.training.plan.service.TrainingPlanUserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
* Service for Training Plan User <br>
* Creation Date : <br>
* date 08/08/2016 <br>
* @author Angela Ramírez
**/
@Service("trainingPlanUserService")
@Transactional
public class TrainingPlanUserServiceImpl implements TrainingPlanUserService {
    
    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;
           
    @Override
    public List<TrainingPlanUser> getTrainingPlanUserByUser(User user) throws Exception {
        return trainingPlanUserDao.getTrainingPlanUserByUser(user);
    }
 
}