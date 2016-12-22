/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.CoachAssignedPlanDao;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CoachAssignedPlanDTO;
import co.com.expertla.training.model.dto.SemaforoDTO;
import co.com.expertla.training.model.entities.CoachAssignedPlan;
import co.com.expertla.training.model.entities.User;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class CoachAssignedPlanDaoImpl extends BaseDAOImpl<CoachAssignedPlan> implements CoachAssignedPlanDao{

    @Override
    public List<CoachAssignedPlanDTO> findByCoachUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new co.com.expertla.training.model.dto.CoachAssignedPlanDTO(m.coachAssignedPlanId, m.trainingPlanUserId.userId, m.starTeamId.coachUserId, m.starTeamId.starUserId, m.starTeamId.starTeamId, cp) ");        
        sql.append(" FROM CoachAssignedPlan m, ConfigurationPlan cp ");
        sql.append(" WHERE m.starTeamId.coachUserId.userId = :userId ");
        sql.append(" AND m.trainingPlanUserId.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND cp.communicationRoleId.roleId = ").append(RoleEnum.COACH_INTERNO.getId());
        sql.append(" AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public CoachAssignedPlanDTO findByAthleteUserId(Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new co.com.expertla.training.model.dto.CoachAssignedPlanDTO(m.coachAssignedPlanId, m.trainingPlanUserId.userId, m.starTeamId.coachUserId, m.starTeamId.starUserId, m.starTeamId.starTeamId, cp) ");
        sql.append(" FROM CoachAssignedPlan  m , ConfigurationPlan cp ");    
        sql.append(" WHERE m.trainingPlanUserId.userId.userId = :userId ");
        sql.append(" AND  m.trainingPlanUserId.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append(" AND cp.communicationRoleId.roleId = ").append(roleSelected);
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<CoachAssignedPlanDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public CoachAssignedPlan findById(Integer id) throws DAOException {
        try {
            String qlString = "SELECT u FROM CoachAssignedPlan u WHERE u.coachAssignedPlanId = :coachAssignedPlanId";
            setParameter("coachAssignedPlanId", id);
            List<CoachAssignedPlan> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<CoachAssignedPlanDTO> findByStarUserId(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.CoachAssignedPlanDTO(m.coachAssignedPlanId, m.trainingPlanUserId.userId, m.starTeamId.coachUserId, m.starTeamId.starUserId, m.starTeamId.starTeamId, m.trainingPlanUserId.trainingPlanId) ");        
        sql.append("FROM CoachAssignedPlan m ");
        sql.append("WHERE m.starTeamId.starUserId.userId = :userId ");
        sql.append("AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<User> findCoachByStarUserId(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT m.coachUserId ");        
        sql.append("FROM StarTeam m ");
        sql.append("WHERE m.starUserId.userId = :userId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<User> findStarByCoachUserId(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT DISTINCT m.starUserId ");        
        sql.append("FROM StarTeam m ");
        sql.append("WHERE m.coachUserId.userId = :userId ");
        sql.append("AND m.starUserId is not null ");
        sql.append("AND m.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    @Override
    public List<SemaforoDTO> findSemaforoByUserId(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.SemaforoDTO( ");
        sql.append(" ");
        sql.append(") FROM CoachAssignedPlan m ");
        sql.append("WHERE m.starTeamId.coachUserId.userId = :userId ");
        sql.append("AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        return query.getResultList();
    }
    
    
}
