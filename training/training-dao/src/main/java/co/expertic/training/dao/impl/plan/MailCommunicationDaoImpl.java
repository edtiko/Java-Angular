package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.plan.MailCommunicationDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.MailCommunicationDTO;
import co.expertic.training.model.dto.PlanMessageDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.MailCommunication;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MailCommunicationDaoImpl extends BaseDAOImpl<MailCommunication> implements MailCommunicationDao {

    @Autowired
    private UserDao userDao;

    @Override
    public MailCommunication findById(Integer mailCommunicationId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT m ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.mailCommunicationId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", mailCommunicationId);
        List<MailCommunication> list = query.getResultList();
        return list == null ? null : list.get(0);
    }

    @Override
    public List<MailCommunicationDTO> getMailsByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public List<MailCommunicationDTO> getMailsByUserIdRead(Integer userId, boolean read) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :id ");
        sql.append("Where m.read = :read ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", userId);
        query.setParameter("read", read);
        return query.getResultList();
    }

    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUser(Integer receivingUserId, Integer sendingUserId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :receivingUser ");
        sql.append("and m.sendingUser.userId = :sendingUser ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("receivingUser", receivingUserId);
        query.setParameter("sendingUser", sendingUserId);
        return query.getResultList();
    }

    @Override
    public List<MailCommunicationDTO> getSentMailsByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.sendingUser.userId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", userId);
        return query.getResultList();
    }

    @Override
    public List<MailCommunicationDTO> getMailsByReceivingUserIdFromSendingUserRead(Integer receivingUserId,
            Integer sendingUserId, boolean read) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        sql.append("Where m.receivingUser.userId = :receivingUser ");
        sql.append("and m.sendingUser.userId = :sendingUser ");
        sql.append("and m.read = :read ");
        sql.append("order by m.creationDate asc ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("receivingUser", receivingUserId);
        query.setParameter("sendingUser", sendingUserId);
        query.setParameter("read", read);
        return query.getResultList();
    }

    @Override
    public List<PlanMessageDTO> getResponseTimeMails(Integer userId, List<UserDTO> users) throws Exception {

        HashMap<Integer, UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(), user);
        }

        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date))  )as seconds ");
        builder.append("from mail_communication  p where (p.sending_user = ").append(userId).append(" or p.receiving_user = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from mail_communication pp where pp.receiving_user = p.sending_user and pp.sending_user = p.receiving_user ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessageUserId(mapUsers.get((Integer) result[1]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[2]));
            obj.setCreationDate((Date) result[6]);
            Double seconds = (Double) result[9];
            obj.setReadableTime(getTime(seconds, obj.getCreationDate()));
            messageList.add(obj);
        }
        return messageList;

    }

    private String getTime(Double seconds, Date creationDate) {
        Double time;
        if (seconds == null) {
            Date now = new Date();
            Long diff = now.getTime() - creationDate.getTime();
            diff = diff / 1000;
            time = diff.doubleValue();
        } else {
            time = seconds;
        }
        if (time > 60) {
            int mins = time.intValue() / 60;
            if (mins > 60) {
                mins = mins / 60;
                return mins + " hrs";
            } else {
                return mins + " mins";
            }
        } else {
            return seconds + " segs";
        }
    }

    @Override
    public List<PlanMessageDTO> getResponseCountMails(Integer userId, List<UserDTO> users) throws Exception {
        HashMap<Integer, UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(), user);
        }
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date)) / 3600 )as hours ");
        builder.append("from mail_communication  p where (p.sending_user = ").append(userId).append(" or p.receiving_user = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from mail_communication pp where pp.receiving_user = p.sending_user and pp.sending_user = p.receiving_user ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanMessageDTO> messageList = new ArrayList<>();
        PlanMessageDTO obj = new PlanMessageDTO();
        for (Object[] result : list) {
            obj = new PlanMessageDTO();
            obj.setMessageUserId(mapUsers.get((Integer) result[1]));
            obj.setReceivingUserId(mapUsers.get((Integer) result[2]));
            obj.setCreationDate((Date) result[6]);
            obj.setHours(result[9] == null ? getHours(obj.getCreationDate()) : (Double) result[9]);
            messageList.add(obj);
        }
        return messageList;
    }

    private Double getHours(Date creationDate) {
        Date now = new Date();
        Long diff = now.getTime() - creationDate.getTime();
        diff = diff / 1000;
        diff = diff / 3600;
        return diff.doubleValue();
    }

    @Override
    public Integer getCountMailsByPlan(Integer planId, Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.email_count  - count(m.mail_communication_id)) > 0 THEN (cp.email_count   - count(m.mail_communication_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN mail_communication m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.sending_user = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = false");
        } else if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.to_star = true");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        if (roleSelected != -1) {
            sql.append(" And cp.communication_role_id = ").append(roleSelected);
        }

        sql.append(" Group by cp.email_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountMailsReceived(Integer planId, Integer sendingUserId, Integer receiveUserId, Integer roleSelected) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.mail_communication_id) ");
        sql.append(" FROM mail_communication m ");
        sql.append(" Where m.sending_user = ").append(sendingUserId);
        sql.append(" And m.receiving_user = ").append(receiveUserId);
        if (planId != -1) {
            sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        }
        sql.append(" And m.read = false");
        if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = false");
        } else if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.to_star = true");
        }
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }
    
    @Override
    public Integer getCountMailsReceivedByUser(Integer receiveUserId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.mail_communication_id) ");
        sql.append(" FROM mail_communication m ");
        sql.append(" WHERE m.receiving_user = ").append(receiveUserId);
        sql.append(" And m.read = false");

        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public Integer getCountMailsByPlanExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.email_count - count(m.mail_communication_id)) > 0 THEN (cp.email_count  - count(m.mail_communication_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp,  coach_ext_athlete c ");
        sql.append(" LEFT JOIN mail_communication m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.sending_user = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.email_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountMailsReceivedExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.mail_communication_id) ");
        sql.append(" FROM mail_communication m ");
        sql.append(" Where m.receiving_user = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" And m.read = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public int getMailsEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.email_count + cp.email_emergency)  - count(m.mail_communication_id)) > 0 THEN ((cp.email_count + cp.email_emergency) - count(m.mail_communication_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN mail_communication m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.sending_user = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = false");
        } else if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.to_star = true");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        if (roleSelected != -1) {
            sql.append(" And cp.communication_role_id = ").append(roleSelected);
        }

        sql.append(" Group by cp.email_count, cp.email_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public int getMailsEmergencyByPlanExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.email_count + cp.email_emergency)  - count(m.mail_communication_id)) > 0 THEN ((cp.email_count + cp.email_emergency) - count(m.mail_communication_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN mail_communication m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.sending_user = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.email_count, cp.email_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public List<MailCommunicationDTO> getMailsByPlan(String tipoPlan, Integer sendingUserId, Integer receivingUserId, Integer planId, Integer roleSelected, String fromto) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.MailCommunicationDTO(m.mailCommunicationId, m.subject,m.message, m.stateId, ");
        sql.append("m.creationDate, m.read,m.receivingUser,m.sendingUser ) ");
        sql.append("FROM MailCommunication m ");
        if (fromto.equals("from")) {
            sql.append("Where m.sendingUser.userId = :sendingUser ");
            sql.append("And m.receivingUser.userId = :receivingUserId ");
        } else {
            sql.append("Where m.receivingUser.userId = :sendingUser ");
            sql.append("And m.sendingUser.userId = :receivingUserId ");
        }
        if (tipoPlan.equals("EXT")) {
            sql.append("And m.coachExtAthleteId.coachExtAthleteId = :planId ");
        } else {
            sql.append("And m.coachAssignedPlanId.coachAssignedPlanId = :planId ");
        }
        if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.toStar = ").append(Boolean.FALSE);
        } else if (roleSelected != 1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.toStar = ").append(Boolean.TRUE);
        }
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("sendingUser", sendingUserId);
        query.setParameter("receivingUserId", receivingUserId);
        query.setParameter("planId", planId);
        return query.getResultList();
    }

    @Override
    public List<UserResumeDTO> getMailsUsersByUserId(Integer userId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.UserResumeDTO(m.sendingUser, r.roleId) ");
        sql.append("FROM MailCommunication m, RoleUser r ");
        sql.append("WHERE m.receivingUser.userId = :userId ");
        sql.append("AND m.sendingUser.userId = r.userId.userId ");
        sql.append("AND m.coachAssignedPlanId is null ");
        sql.append("AND m.coachExtAthleteId is null ");
        sql.append("GROUP BY m.sendingUser, r.roleId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<UserResumeDTO> list = query.getResultList();

        if (list == null || list.isEmpty()) {
            sql = new StringBuilder();
            sql.append("SELECT new co.expertic.training.model.dto.UserResumeDTO(m.starUserId, r.roleId) ");
            sql.append("FROM StarTeam m, RoleUser r ");
            sql.append("WHERE ((m.starUserId.userId = :userId ");
            sql.append("AND m.starUserId.userId = r.userId.userId) ");
            sql.append("OR (m.coachUserId.userId = :userId ");
            sql.append("AND m.starUserId.userId = r.userId.userId ))");
            sql.append("AND m.stateId = :active ");
            query = getEntityManager().createQuery(sql.toString());
            query.setParameter("userId", userId);
            query.setParameter("active", StateEnum.ACTIVE.getId().shortValue());
            list = query.getResultList();
        }
        return list;
    }
}
