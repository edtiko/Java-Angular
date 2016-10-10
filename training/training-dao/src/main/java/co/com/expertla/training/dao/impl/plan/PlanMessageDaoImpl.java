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
import java.util.ArrayList;
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
    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");     
        sql.append(" WHEN (t.message_count  - count(m.plan_message_id)) >= 0 THEN (t.message_count  - count(m.plan_message_id)) ");
        sql.append(" ELSE t.message_count END ");
        sql.append(" FROM training_plan_user tu, training_plan t, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_message m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.message_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" Group by t.message_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0?count.get(0).intValue():0;
    }
    
    @Override
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException{
      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_message_id) ");     
        sql.append(" FROM plan_message m ");
        sql.append(" Where m.message_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    } 

    @Override
    public void readMessages(Integer coachAssignedPlanId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_message ");
        builder.append(" set readed = true ");
        builder.append(" where  message_user_id = ").append(userId);
        builder.append(" and  coach_assigned_plan_id = ").append(coachAssignedPlanId);
        executeNativeUpdate(builder.toString());
    }
    
    @Override
     public void readMessage(Integer planMessageId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_message ");
        builder.append(" set readed = true ");
        builder.append(" where  plan_message_id = ").append(planMessageId);
        executeNativeUpdate(builder.toString());
    }
    
     @Override
    public List<PlanMessageDTO> getMessagesByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId)throws  Exception {
        List<Integer> ids = new ArrayList<>();
        ids.add(sendingUserId);
        ids.add(receivingUserId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanMessageDTO(m.planMessageId,m.message, m.messageUserId, m.creationDate, m.receivingUserId) ");        
        sql.append("FROM PlanMessage m ");
        sql.append("Where m.messageUserId.userId in :ids ");
        sql.append("AND m.receivingUserId.userId in :ids");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("ids", ids);
        return query.getResultList();
    }
    
}
