package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.ObjectiveDao;
import co.expertic.training.dao.configuration.TrainingLevelDao;
import co.expertic.training.model.dto.ObjectiveDTO;
import co.expertic.training.model.dto.TrainingLevelDTO;
import co.expertic.training.model.entities.Objective;
import co.expertic.training.service.configuration.ObjectiveService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Objective Service Impl <br>
 * Info. Creación: <br>
 * fecha 30/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@Service("objectiveService")
@Transactional
public class ObjectiveServiceImpl implements ObjectiveService {

    @Autowired
    private ObjectiveDao objectiveDao;
    
    @Autowired
    private TrainingLevelDao levelDao;

    @Override
    public Objective create(Objective objective) throws Exception {
        return objectiveDao.create(objective);
    }

    @Override
    public Objective store(Objective objective) throws Exception {
        return objectiveDao.merge(objective);
    }

    @Override
    public void remove(Objective objective) throws Exception {
        objectiveDao.remove(objective, objective.getObjectiveId());
    }

    @Override
    public List<ObjectiveDTO> findAll() throws Exception {
        return objectiveDao.findAll();
    }

    @Override
    public List<Objective> findAllActive() throws Exception {
        return objectiveDao.findAllActive();
    }

    @Override
    public List<ObjectiveDTO> findPaginate(int first, int max, String order) throws Exception {
        return objectiveDao.findPaginate(first, max, order);
    }

    @Override
    public List<Objective> findByObjective(Objective objective) throws Exception {
        return objectiveDao.findByObjective(objective);
    }

    @Override
    public List<Objective> findByFiltro(Objective objective) throws Exception {
        return objectiveDao.findByFiltro(objective);
    }

    @Override
    public List<ObjectiveDTO> findByDiscipline(Integer disciplineId) throws Exception {
        return objectiveDao.findByDiscipline(disciplineId);
    }

    @Override
    public Integer findNextObjective(Integer trainingUserPlanId) throws Exception {
        return objectiveDao.findNextObjective(trainingUserPlanId);
    }

    @Override
    public Integer findCurrentObjective(Integer trainingUserPlanId) throws Exception {
        return objectiveDao.findCurrentObjective(trainingUserPlanId);
    }

    @Override
    public List<TrainingLevelDTO> findByModality(Integer modalityId) throws Exception {
        return levelDao.findByModality(modalityId);
    }

}
