/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.plan;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.User;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface CoachAssignedPlanDao extends BaseDAO<CoachAssignedPlan>{
    
    List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId) throws DAOException;
    
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
}
