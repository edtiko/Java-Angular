package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.TrainingPlanCharactDao;
import co.expertic.training.model.entities.TrainingPlanCharact;
import co.expertic.training.service.configuration.TrainingPlanCharactService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* TrainingPlanCharact Service Impl <br>
* Info. Creación: <br>
* fecha 9/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("trainingPlanCharactService")
@Transactional
public class TrainingPlanCharactServiceImpl implements TrainingPlanCharactService {

    @Autowired
    private TrainingPlanCharactDao trainingPlanCharactDao;

    @Override
    public TrainingPlanCharact create(TrainingPlanCharact trainingPlanCharact) throws Exception {
        return trainingPlanCharactDao.create(trainingPlanCharact);
    }

    @Override
    public TrainingPlanCharact store(TrainingPlanCharact trainingPlanCharact) throws Exception {
       return trainingPlanCharactDao.merge(trainingPlanCharact);
    }

    @Override
    public void remove(TrainingPlanCharact trainingPlanCharact) throws Exception {
        trainingPlanCharactDao.remove(trainingPlanCharact, trainingPlanCharact.getTrainingPlanCharactId());
    }

    @Override
    public List<TrainingPlanCharact> findAll() throws Exception {
        return trainingPlanCharactDao.findAll();
    }

    @Override
    public List<TrainingPlanCharact> findAllActive() throws Exception {
        return trainingPlanCharactDao.findAllActive();
    }

    @Override
    public List<TrainingPlanCharact> findByTrainingPlanCharact(TrainingPlanCharact trainingPlanCharact) throws Exception {
        return trainingPlanCharactDao.findByTrainingPlanCharact(trainingPlanCharact);
    }

    @Override
    public List<TrainingPlanCharact> findByFiltro(TrainingPlanCharact trainingPlanCharact) throws Exception {
        return trainingPlanCharactDao.findByFiltro(trainingPlanCharact);
    }

}