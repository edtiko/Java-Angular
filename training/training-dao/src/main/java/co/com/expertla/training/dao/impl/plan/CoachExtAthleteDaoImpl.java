/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.CoachExtAthleteDao;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.CoachExtAthlete;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class CoachExtAthleteDaoImpl extends BaseDAOImpl<CoachExtAthlete> implements CoachExtAthleteDao{

    @Override
    public List<CoachExtAthleteDTO> getAthletes(Integer trainingPlanUserId, String state) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.CoachExtAthleteDTO(m.coachExtAthleteId, m.userTrainingId, m.trainingPlanUserId.userId, cp, m.creationDate, m.stateId )  ");
        sql.append("FROM CoachExtAthlete m, ConfigurationPlan cp  ");
        sql.append("Where m.trainingPlanUserId.trainingPlanUserId = :trainingPlanUserId ");
        sql.append(" AND m.trainingPlanUserId.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND cp.communicationRoleId.roleId = ").append(RoleEnum.COACH.getId());
        if (!state.equals("ALL")) {
            sql.append(" And m.stateId.stateId = :stateId ");
        }
        sql.append(" Order by m.creationDate desc");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingPlanUserId", trainingPlanUserId);
        if (!state.equals("ALL")) {
            query.setParameter("stateId", StateEnum.valueOf(state).getId());
        }
        return query.getResultList();
    }

    @Override
    public CoachExtAthlete findById(Integer coachExtAthleteId) throws DAOException {
           try {
            String qlString = "SELECT e FROM CoachExtAthlete e WHERE e.coachExtAthleteId = :coachExtAthleteId";
            setParameter("coachExtAthleteId", coachExtAthleteId);
            List<CoachExtAthlete> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CoachExtAthleteDTO findByAthleteUserId(Integer athleteUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new co.com.expertla.training.model.dto.CoachExtAthleteDTO(m.coachExtAthleteId, m.userTrainingId, m.trainingPlanUserId.userId, cp, m.creationDate, m.stateId ) ");
        sql.append(" FROM CoachExtAthlete m, ConfigurationPlan cp ");
        sql.append(" WHERE m.userTrainingId.userId = :userId ");
        sql.append(" AND m.trainingPlanUserId.trainingPlanId.trainingPlanId = cp.trainingPlanId.trainingPlanId ");
        sql.append(" AND cp.communicationRoleId.roleId = ").append(RoleEnum.COACH.getId());
        sql.append(" AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append(" AND m.stateId.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", athleteUserId);
        List<CoachExtAthleteDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }

    @Override
    public List<UserDTO> getUserAthletes(String search) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new co.com.expertla.training.model.dto.UserDTO(u.userId, u.name, u.secondName, u.lastName, u.email, u.login, u.profilePhoto) ");
        sql.append(" FROM User u, RoleUser ru ");
        sql.append(" WHERE not exists (select 1 from CoachExtAthlete   c where u.userId = c.userTrainingId.userId and c.stateId.stateId = :stateId ) ");
        sql.append(" And   not exists (select 1 from CoachAssignedPlan c where u.userId = c.trainingPlanUserId.userId.userId )");
        sql.append(" And   u.userId = ru.userId.userId ");
        sql.append(" And   ru.roleId.roleId = :role ");
        if(!search.trim().equals("ALL")){
             sql.append(" and concat(u.name,' ', u.secondName, ' ', u.lastName) like :search ");
        }
        sql.append(" ORDER BY u.name ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("stateId", StateEnum.ACTIVE.getId());
        query.setParameter("role", RoleEnum.ATLETA.getId());
          if(search != null && !search.trim().equals("ALL")){
              query.setParameter("search", "%"+search+"%");
          }
        List<UserDTO> list = query.getResultList();

        return list;
    }

    @Override
    public CoachExtAthleteDTO getInvitation(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT new co.com.expertla.training.model.dto.CoachExtAthleteDTO(m.coachExtAthleteId, m.userTrainingId, m.trainingPlanUserId.userId,  m.creationDate, m.stateId ) ");
        sql.append(" FROM CoachExtAthlete m ");
        sql.append(" WHERE m.userTrainingId.userId = :userId ");
        sql.append(" AND m.trainingPlanUserId.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append(" AND m.stateId.stateId = ").append(StateEnum.PENDING.getId());
        sql.append(" ORDER BY m.creationDate ASC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<CoachExtAthleteDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
}
