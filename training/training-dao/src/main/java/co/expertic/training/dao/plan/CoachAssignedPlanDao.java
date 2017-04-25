/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.dto.CoachAssignedPlanDTO;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.ReportCountDTO;
import co.expertic.training.model.dto.SemaforoDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.CoachAssignedPlan;
import co.expertic.training.model.entities.User;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanDao extends BaseDAO<CoachAssignedPlan>{
    
    List<CoachAssignedPlanDTO> findAthletesByUserRole(Integer userId, Integer roleId, PaginateDto paginateDto) throws DAOException;
    
    CoachAssignedPlanDTO findByAthleteUserId(Integer userId) throws DAOException;
    
    public CoachAssignedPlan findById(Integer id) throws DAOException;
    
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
    
    public List<User> findCoachByStarUserId(Integer userId) throws Exception;
    
    public List<User> findStarByCoachUserId(Integer userId) throws Exception;
    
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception;
    
    public List<ReportCountDTO> getCountByPlanRole(Integer userId, Integer roleId) throws Exception; 

    public List<UserResumeDTO> findAthletesByCoachUserId(Integer coachUserId) throws DAOException;
}
