package co.expertic.training.service.configuration;

import co.expertic.training.model.entities.TrainingPlanCharact;
import java.util.List;

/**
* TrainingPlanCharact Service <br>
* Info. Creación: <br>
* fecha 9/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanCharactService {
    

    /**
     * Crea trainingPlanCharact <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanCharact
     * @return 
     * @throws Exception 
     */
    public TrainingPlanCharact create(TrainingPlanCharact trainingPlanCharact) throws Exception;
    /**
     * Modifica trainingPlanCharact <br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanCharact
     * @return 
     * @throws Exception 
     */
    public TrainingPlanCharact store(TrainingPlanCharact trainingPlanCharact) throws Exception;
    /**
     * Elimina trainingPlanCharact<br>
     * Info. Creación: <br>
     * fecha 9/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanCharact
     * @throws Exception 
     */
    public void remove(TrainingPlanCharact trainingPlanCharact) throws Exception;
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
     * Obtiene todos los registros de trainingPlanCharact paginados <br>
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