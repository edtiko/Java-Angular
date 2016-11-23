/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.PlanMessageDao;
import co.com.expertla.training.dao.user.UserDao;
import co.com.expertla.training.enums.RoleEnum;
import co.com.expertla.training.model.dto.PlanMessageDTO;
import co.com.expertla.training.model.dto.UserDTO;
import co.com.expertla.training.model.entities.PlanMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanMessageDaoImpl extends BaseDAOImpl<PlanMessage> implements PlanMessageDao {
    
    @Autowired
    private UserDao userDao;

    @Override
    public List<PlanMessageDTO> getMessagesByPlan(Integer planId, String tipoPlan, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanMessageDTO(m.planMessageId,m.message, m.messageUserId, m.creationDate) ");        
        sql.append("FROM PlanMessage m ");
        if(tipoPlan.equals("IN")){
        sql.append("Where m.coachAssignedPlanId.coachAssignedPlanId = :planId ");
        }else{
        sql.append("Where m.coachExtAthleteId.coachExtAthleteId = :planId ");  
        }
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" and  m.toStar = ").append(Boolean.FALSE);
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" and  m.toStar =  ").append(Boolean.TRUE);
        }
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("planId", planId);
        return query.getResultList();
    }

    @Override
    public Integer getCountMessagesByPlan(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");     
        sql.append(" WHEN (cp.message_count  - count(m.plan_message_id)) > 0 THEN (cp.message_count   - count(m.plan_message_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_message m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.message_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" and  m.to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" and  m.to_star = true ");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id =  ").append(roleSelected);
        sql.append(" Group by cp.message_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0?count.get(0).intValue():0;
    }
    
    @Override
    public Integer getCountMessagesReceived(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException{
      
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_message_id) ");     
        sql.append(" FROM plan_message m ");
        sql.append(" Where m.message_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
            if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" and m.to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" and m.to_star = true ");
        }
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    } 

    @Override
    public void readMessages(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_message ");
        builder.append(" set readed = true ");
        builder.append(" where  message_user_id = ").append(userId);
        builder.append(" and  coach_assigned_plan_id = ").append(coachAssignedPlanId);
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            builder.append(" and  to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            builder.append(" and  to_star = true ");
        }
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
    public Integer getCountMessagesByPlanExt(Integer planId, Integer userId) throws DAOException {
                StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");     
        sql.append(" WHEN (cp.message_count - count(m.plan_message_id)) > 0 THEN (cp.message_count  - count(m.plan_message_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_message m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.message_user_id = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id =  ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.message_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0?count.get(0).intValue():0;
    }

    @Override
    public Integer getCountMessagesReceivedExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_message_id) ");     
        sql.append(" FROM plan_message m ");
        sql.append(" Where m.message_user_id = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());
       
        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }
    
    @Override
    public void readMessagesExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_message ");
        builder.append(" set readed = true ");
        builder.append(" where  message_user_id = ").append(userId);
        builder.append(" and  coach_ext_athlete_id = ").append(planId);
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
    
    @Override
    public List<PlanMessageDTO> getResponseTimeMessages(Integer userId, List<UserDTO> users)throws  Exception {
        
            HashMap<Integer,UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(),user);
        }
        
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date))  )as seconds ");
        builder.append("from plan_message  p where (p.message_user_id = ").append(userId).append(" or p.receiving_user_id = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from plan_message pp where pp.receiving_user_id = p.message_user_id and pp.message_user_id = p.receiving_user_id ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessage((String) result[2]);
            obj.setMessageUserId(mapUsers.get((Integer) result[5]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[7]));
            obj.setCreationDate((Date)result[4]);
            Double seconds = (Double)result[9];
            obj.setReadableTime(getTime(seconds,obj.getCreationDate()));
            messageList.add(obj);
        }
        return messageList;
    
    }
    
    private String getTime(Double seconds, Date creationDate ){
        Double time;
        if(seconds == null) {
            Date now = new Date();
            Long diff = now.getTime() - creationDate.getTime();
            diff = diff /1000;
            time = diff.doubleValue();
        } else {
            time = seconds;
        }
         if(time > 60) {
             int mins = time.intValue() / 60;
             if(mins > 60) {
                 mins = mins /60;
                 return mins + " hrs";
             } else {
                 return mins + " mins";
             }
         } else {
             return seconds + " segs";
         }
    }
    
    @Override
    public List<PlanMessageDTO> getResponseCountMessages(Integer userId,List<UserDTO> users) throws Exception {
        HashMap<Integer,UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(),user);
        }
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date)) / 3600 )as hours ");
        builder.append("from plan_message  p where (p.message_user_id = ").append(userId).append(" or p.receiving_user_id = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from plan_message pp where pp.receiving_user_id = p.message_user_id and pp.message_user_id = p.receiving_user_id ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessage((String) result[2]);
            obj.setMessageUserId(mapUsers.get((Integer) result[5]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[7]));
            obj.setCreationDate((Date)result[4]);
            obj.setHours( result[9] == null ? getHours(obj.getCreationDate()) : (Double) result[9]);
            messageList.add(obj);
        }
        return messageList;
    }
    
    private Double getHours(Date creationDate ){
          Date now = new Date();
            Long diff = now.getTime() - creationDate.getTime();
            diff = diff /1000;
            diff = diff / 3600;
            return diff.doubleValue();
    }

    @Override
    public List<PlanMessageDTO> getMessagesNotReadedByReceivingUserAndSendingUser(Integer receivingUserId, Integer sendingUserId) throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.add(sendingUserId);
        ids.add(receivingUserId);
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanMessageDTO(m.planMessageId,m.message, m.messageUserId, m.creationDate, m.receivingUserId) ");        
        sql.append("FROM PlanMessage m ");
        sql.append("Where m.messageUserId.userId = :ids ");
        sql.append("AND m.receivingUserId.userId = :receivingUserId ");
        sql.append("AND (m.readed = :read OR m.readed = null) ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("ids", sendingUserId);
        query.setParameter("receivingUserId", receivingUserId);
        query.setParameter("read", false);
        return query.getResultList();
    }
    
        @Override
    public int getCountMessageEmergencyIn(Integer planId, Integer fromUserId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.message_count + cp.message_emergency)  - count(m.plan_message_id)) > 0 THEN ((cp.message_count + cp.message_emergency) - count(m.plan_message_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_message m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.message_user_id = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" and  m.to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" and  m.to_star = true ");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(roleSelected);
        sql.append(" Group by cp.message_count, cp.message_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public int getCountMessageEmergencyExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.message_count + cp.message_emergency)  - count(m.plan_message_id)) > 0 THEN ((cp.message_count + cp.message_emergency) - count(m.plan_message_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_message m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.message_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.message_count, cp.message_emergency  ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }
}
