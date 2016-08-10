package co.com.expertla.training.plan.service;

import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.model.entities.User;
import java.util.List;

/**
* Service for TrainingPlanUserService <br>
* Creation Date : <br>
* date 08/08/2016 <br>
* @author Angela Ramírez
**/
public interface TrainingPlanUserService {
     
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