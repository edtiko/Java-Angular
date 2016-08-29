/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.PlanMessageDao;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.entities.PlanMessage;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanMessageDaoImpl extends BaseDAOImpl<PlanMessage> implements PlanMessageDao {

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer coachAssignedPlanId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanMessageDTO(m.planMessageId,m.message, m.messageUserId, m.creationDate) ");        
        sql.append("FROM PlanMessage m ");
        sql.append("Where m.coachAssignedPlanId.coachAssignedPlanId = :coachAssignedPlanId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("coachAssignedPlanId", coachAssignedPlanId);
        return query.getResultList();
    }

    @Override
    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t.message_count  - count(m.plan_message_id) ");        
        sql.append("FROM plan_message m, coach_assigned_plan c, training_plan_user tu, training_plan t ");
        sql.append("Where m.coach_assigned_plan_id = c.coach_assigned_plan_id ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And c.training_plan_user_id  = tu.training_plan_user_id ");
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" Group by t.message_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());
        //query.setParameter("coachAssignedPlanId", coachAssignedPlanId);
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0?count.get(0).intValue():0;
    }
    
}
