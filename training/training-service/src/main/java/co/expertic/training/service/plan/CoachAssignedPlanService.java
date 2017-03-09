/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.plan;

import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.SemaforoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanService {
    
    List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId, PaginateDto paginateDto) throws Exception;
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
    
    public List<UserDTO> findCoachByStarUserId(Integer userId) throws Exception;
    
    public List<UserDTO> findStarByCoachUserId(Integer userId) throws Exception;
    
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception;
    
    public List<ReportCountDTO> getCountByPlanCoach(Integer userId) throws Exception; 
    
}
