/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.service.plan;

import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanService {
    
    List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId) throws Exception;
    CoachAssignedPlanDTO findByAthleteUserId(Integer userId) throws Exception;
    
    
    /**
     * Crea coachAssignedPlan <br>
     * Info. Creación: <br>
     * fecha 13/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param coachAssignedPlan
     * @return 
     * @throws Exception 
     */
    public CoachAssignedPlan create(CoachAssignedPlan coachAssignedPlan) throws Exception;
    
    /**
     * Consulta coachAssignedPlan por star user Id <br>
     * Info. Creación: <br>
     * fecha 13/09/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @return 
     * @throws Exception 
     */
    public List<CoachAssignedPlanDTO> findByStarUserId(Integer userId) throws Exception;
    
}
