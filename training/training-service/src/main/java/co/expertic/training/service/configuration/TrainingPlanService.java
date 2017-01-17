package co.expertic.training.service.configuration;

import co.expertic.training.model.dto.TrainingPlanDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.TrainingPlan;
import java.util.List;

/**
* TrainingPlan Service <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanService {
    

    /**
     * Crea trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return 
     * @throws Exception 
     */
    public TrainingPlan create(TrainingPlan trainingPlan) throws Exception;
    /**
     * Modifica trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return 
     * @throws Exception 
     */
    public TrainingPlan store(TrainingPlan trainingPlan) throws Exception;
    /**
     * Elimina trainingPlan<br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @throws Exception 
     */
    public void remove(TrainingPlan trainingPlan) throws Exception;
    /**
     * Obtiene todos los registros de trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de trainingPlan paginados <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanDTO> findPaginate(int first, int max, String order, String filter, String typePlan) throws Exception;
    
    /**
     * Obtiene todos los registros de trainingPlan paginados <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findByTrainingPlan(TrainingPlan trainingPlan) throws Exception;   

    /**
     * Obtiene todos los registros de trainingPlan por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlan
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findByFiltro(TrainingPlan trainingPlan) throws Exception; 


    /**
    * Obtiene todos los registros de trainingPlan por nombre <br>
    * Info. Creación: <br>
    * fecha 23/11/2016 <br>
    * @author Andres Felipe Lopez Rodriguez
    * @param trainingPlan
    * @return
    * @throws Exception 
    */
    public List<TrainingPlan> findByName(TrainingPlan trainingPlan) throws Exception;  
    
    /**
     * Obtiene todos los registros activos de trainingPlan <br>
     * Info. Creación: <br>
     * fecha 23/11/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findPlaformAllActive(String typePlan) throws Exception;

    public List<UserDTO> findDefaultSupervisors()throws Exception;

    
}