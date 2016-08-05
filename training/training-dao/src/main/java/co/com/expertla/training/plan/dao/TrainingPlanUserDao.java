package co.com.expertla.training.plan.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import java.util.List;

/**
 * Dao for Training Plan User <br>
 * Creation Date : <br>
 * date 25/07/2016 <br>
 *
 * @author Angela Ramírez
*
 */
public interface TrainingPlanUserDao extends BaseDAO<TrainingPlanUser> {
    
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
}
