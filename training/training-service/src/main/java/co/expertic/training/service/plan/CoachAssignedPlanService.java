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
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanService {
    
    List<CoachAssignedPlanDTO> findAthletesByUserRole(Integer userId, Integer roleId, PaginateDto paginateDto) throws Exception;
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
    
    public List<UserResumeDTO> findStarByCoachUserId(Integer userId) throws Exception;
    
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception;
    
    public List<ReportCountDTO> getCountByPlanRole(Integer userId, Integer roleId) throws Exception; 

    public List<UserResumeDTO> findAthletesByCoachUserId(Integer coachUserId) throws Exception;

    public CoachAssignedPlanDTO findByStarAthleteUserId(Integer athleteUserId, Integer starUserId)  throws Exception;

    public void setStarManageMessages(Integer planId) throws Exception;

    public List<UserResumeDTO> findAthletesByCoachStar(Integer coachUserId, Integer starUserId) throws Exception;

}
