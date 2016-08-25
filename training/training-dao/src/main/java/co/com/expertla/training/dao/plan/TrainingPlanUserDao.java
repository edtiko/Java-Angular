package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import java.util.List;

/**
* TrainingPlanUser Dao <br>
* Info. Creación: <br>
* fecha 10/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanUserDao extends BaseDAO<TrainingPlanUser> {
    
    
    /**
     * Obtiene todos los registros de trainingPlanUser <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanUser> findAll() throws Exception;

    /**
     * Obtiene el plan activo por usuario <br>
     * Info. Creación: <br>
     * fecha 05/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanUser> getPlanWorkoutByUser(Integer userId) throws Exception;
    /**
     * Obtiene todos los registros de trainingPlanUser por su id <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanUser> findByTrainingPlanUser(TrainingPlanUser trainingPlanUser) throws Exception;   

    /**
     * Obtiene todos los registros de trainingPlanUser por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 10/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanUser
     * @return
     * @throws Exception 
     */
    public List<TrainingPlanUser> findByFiltro(TrainingPlanUser trainingPlanUser) throws Exception; 

	/**
     * Obtiene el TrainingPlanUser por user Id <br>
     * Creation Date : <br>
     * date 08/08/2016 <br>
     * @author Angela Ramírez
     * @param user
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanUser> getTrainingPlanUserByUser(User user) throws Exception;

}
