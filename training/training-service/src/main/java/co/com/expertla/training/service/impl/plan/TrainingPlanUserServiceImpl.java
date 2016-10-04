package co.com.expertla.training.service.impl.plan;

import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.dao.plan.TrainingPlanUserDao;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import java.util.List;
import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.dao.plan.TrainingPlanUserDao;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* TrainingPlanUser Service Impl <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
=======

/**
* Service for Training Plan User <br>
* Creation Date : <br>
* date 08/08/2016 <br>
* @author Angela Ramírez
>>>>>>> origin/RF-PP-003_perfil
**/
@Service("trainingPlanUserService")
@Transactional
public class TrainingPlanUserServiceImpl implements TrainingPlanUserService {

    @Autowired
    private TrainingPlanUserDao trainingPlanUserDao;

    @Override
    public TrainingPlanUser create(TrainingPlanUser trainingPlanUser) throws Exception {
        return trainingPlanUserDao.create(trainingPlanUser);
    }

    @Override
    public TrainingPlanUser store(TrainingPlanUser trainingPlanUser) throws Exception {
       return trainingPlanUserDao.merge(trainingPlanUser);
    }

    @Override
    public void remove(TrainingPlanUser trainingPlanUser) throws Exception {
        trainingPlanUserDao.remove(trainingPlanUser, trainingPlanUser.getTrainingPlanUserId());
    }

    @Override
    public List<TrainingPlanUser> findAll() throws Exception {
        return trainingPlanUserDao.findAll();
    }

    
    @Override
    public List<TrainingPlanUser> getPlanWorkoutByUser(Integer userId) throws Exception {
        return trainingPlanUserDao.getPlanWorkoutByUser(userId);
    }

    @Override
    public List<TrainingPlanUser> findByTrainingPlanUser(TrainingPlanUser trainingPlanUser) throws Exception {
        return trainingPlanUserDao.findByTrainingPlanUser(trainingPlanUser);
    }

    @Override
    public List<TrainingPlanUser> findByFiltro(TrainingPlanUser trainingPlanUser) throws Exception {
        return trainingPlanUserDao.findByFiltro(trainingPlanUser);
    }

    @Override
    public List<TrainingPlanUser> getTrainingPlanUserByUser(User user) throws Exception {
        return trainingPlanUserDao.getTrainingPlanUserByUser(user);
    }
 
}