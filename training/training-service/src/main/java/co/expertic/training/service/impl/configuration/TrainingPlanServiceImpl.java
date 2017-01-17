package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.TrainingPlanDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.model.dto.TrainingPlanDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.TrainingPlan;
import co.expertic.training.service.configuration.TrainingPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* TrainingPlan Service Impl <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("trainingPlanService")
@Transactional
public class TrainingPlanServiceImpl implements TrainingPlanService {

    @Autowired
    private TrainingPlanDao trainingPlanDao;
    
      @Autowired
    private UserDao userDao;

    @Override
    public TrainingPlan create(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.create(trainingPlan);
    }

    @Override
    public TrainingPlan store(TrainingPlan trainingPlan) throws Exception {
       return trainingPlanDao.merge(trainingPlan);
    }

    @Override
    public void remove(TrainingPlan trainingPlan) throws Exception {
        trainingPlanDao.remove(trainingPlan, trainingPlan.getTrainingPlanId());
    }

    @Override
    public List<TrainingPlan> findAll() throws Exception {
        return trainingPlanDao.findAll();
    }

    @Override
    public List<TrainingPlan> findAllActive() throws Exception {
        return trainingPlanDao.findAllActive();
    }

    @Override
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order, String filter, String typePlan) throws Exception {
        return trainingPlanDao.findPaginate(first, max, order, filter, typePlan);
    }

    @Override
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.findByTrainingPlan(trainingPlan);
    }

    @Override
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.findByFiltro(trainingPlan);
    }

    @Override
    public List<TrainingPlan> findByName(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.findByName(trainingPlan);
    }
    
    @Override
    public List<TrainingPlan> findPlaformAllActive(String typePlan) throws Exception {
        return trainingPlanDao.findPlaformAllActive(typePlan);
    }

    @Override
    public List<UserDTO> findDefaultSupervisors() throws Exception {
       return userDao.findDefaultSupervisors();
    }
}