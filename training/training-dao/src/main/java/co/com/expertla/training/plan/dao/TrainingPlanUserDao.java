package co.com.expertla.training.plan.dao;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import java.util.List;

/**
* Dao for Training Plan User <br>
* Creation Date : <br>
* date 25/07/2016 <br>
* @author Angela Ramírez
**/
public interface TrainingPlanUserDao extends BaseDAO<TrainingPlanUser>{

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
