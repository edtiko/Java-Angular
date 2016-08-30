package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.TrainingPlanDao;
import co.com.expertla.training.model.dto.TrainingPlanDTO;
import co.com.expertla.training.model.entities.TrainingPlan;
import co.com.expertla.training.service.configuration.TrainingPlanService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* TrainingPlan Service Impl <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("trainingPlanService")
@Transactional
public class TrainingPlanServiceImpl implements TrainingPlanService {

    @Autowired
    private TrainingPlanDao trainingPlanDao;

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
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order) throws Exception {
        return trainingPlanDao.findPaginate(first, max, order);
    }

    @Override
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.findByTrainingPlan(trainingPlan);
    }

    @Override
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception {
        return trainingPlanDao.findByFiltro(trainingPlan);
    }

}