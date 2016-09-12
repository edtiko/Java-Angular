package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.TrainingPlanCharact;
import java.util.List;

/**
* TrainingPlanCharact Dao <br>
* Info. Creación: <br>
* fecha 9/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanCharactDao extends BaseDAO<TrainingPlanCharact> {
    
    
    /**
     * Obtiene todos los registros de trainingPlanCharact <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanCharact> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de trainingPlanCharact <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanCharact> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de trainingPlanCharact por su id <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanCharact
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanCharact> findByTrainingPlanCharact(TrainingPlanCharact trainingPlanCharact) throws Exception;   

    /**
     * Obtiene todos los registros de trainingPlanCharact por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanCharact
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanCharact> findByFiltro(TrainingPlanCharact trainingPlanCharact) throws Exception; 

}
