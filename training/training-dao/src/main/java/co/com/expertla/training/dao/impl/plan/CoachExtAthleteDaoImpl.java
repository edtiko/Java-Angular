/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.CoachExtAthleteDao;
import co.com.expertla.training.enums.StateEnum;
import co.com.expertla.training.model.dto.CoachExtAthleteDTO;
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
        sql.append("SELECT new co.com.expertla.training.model.dto.CoachExtAthleteDTO(m.coachExtAthleteId, m.userTrainingId.userId, m.trainingPlanUserId.userId, m.trainingPlanUserId.trainingPlanId, m.creationDate, m.stateId ) ");
        sql.append("FROM CoachExtAthlete m ");
        sql.append("Where m.trainingPlanUserId.trainingPlanUserId = :trainingPlanUserId ");
        if (!state.equals("ALL")) {
            sql.append("And m.stateId.stateId = :stateId ");
        }
        sql.append("Order by m.creationDate desc");
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
        sql.append("SELECT new co.com.expertla.training.model.dto.CoachExtAthleteDTO(m.coachExtAthleteId, m.userTrainingId.userId, m.trainingPlanUserId.userId, m.trainingPlanUserId.trainingPlanId, m.creationDate, m.stateId ) ");
        sql.append("FROM CoachExtAthlete m ");
        sql.append("WHERE m.userTrainingId.userId = :userId ");
        sql.append("AND m.trainingPlanUserId.stateId.stateId = ").append(StateEnum.ACTIVE.getId());
        sql.append("AND m.stateId.stateId = ").append(StateEnum.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", athleteUserId);
        List<CoachExtAthleteDTO> list = query.getResultList();
        return (list == null || list.isEmpty()) ? null : list.get(0);
    }
    
}
