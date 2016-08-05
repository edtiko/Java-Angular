/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.plan.service;

import co.com.expertla.training.model.entities.TrainingPlanUser;
import java.util.List;

/**
 * Service for Training Plan User <br>
 * Creation Date : <br>
 * date 05/08/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
public interface TrainingPlanUserService {
    
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
