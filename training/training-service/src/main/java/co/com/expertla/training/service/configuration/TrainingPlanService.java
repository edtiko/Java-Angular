package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.dto.TrainingPlanDTO;
import co.com.expertla.training.model.entities.TrainingPlan;
import java.util.List;

/**
* TrainingPlan Service <br>
* Info. Creación: <br>
* fecha 30/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanService {
    

    /**
     * Crea trainingPlan <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return 
     * @throws Exception 
     */
    public TrainingPlan create(TrainingPlan trainingPlan) throws Exception;
    /**
     * Modifica trainingPlan <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @throws Exception 
     */
    public TrainingPlan store(TrainingPlan trainingPlan) throws Exception;
    /**
     * Elimina trainingPlan<br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return 
     * @throws Exception 
     */
    public void remove(TrainingPlan trainingPlan) throws Exception;
    /**
     * Obtiene todos los registros de trainingPlan <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de trainingPlan <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de trainingPlan paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order) throws Exception;
    
    /**
     * Obtiene todos los registros de trainingPlan paginados <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception;   

    /**
     * Obtiene todos los registros de trainingPlan por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 30/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception; 



    
}