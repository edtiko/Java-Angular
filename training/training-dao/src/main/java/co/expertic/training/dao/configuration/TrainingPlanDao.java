package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.ReportDTO;
import co.expertic.training.model.dto.TrainingPlanDTO;
import co.expertic.training.model.entities.TrainingPlan;
import java.util.List;

/**
* TrainingPlan Dao <br>
* Info. Creación: <br>
* fecha 23/11/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanDao extends BaseDAO<TrainingPlan> {
    
    
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
     * Obtiene todos los registros de trainingPlan por su id <br>
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
     * @param typePlan
     * @return
     * @throws Exception 
     */
    public List<TrainingPlan> findPlaformAllActive(String typePlan) throws Exception;

    public List<List<ReportCountDTO>> findSaleReport(ReportDTO paginateDto)throws Exception;

    public List<ReportCountDTO> findPaymentReport(ReportDTO paginateDto)throws Exception;
}
