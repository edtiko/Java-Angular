package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.plan.PlanAudioDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.model.dto.PlanAudioDTO;
import co.expertic.training.model.entities.PlanAudio;
import java.util.List;
import java.util.Objects;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanAudioDaoImpl extends BaseDAOImpl<PlanAudio> implements PlanAudioDao {

    @Override
    public PlanAudio getByAudioPath(String fileName) throws DAOException {
        try {
            String qlString = "SELECT v FROM PlanAudio v WHERE v.name = :name ";
            setParameter("name", fileName);
            List<PlanAudio> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlanAudioDTO> getAudiosByUser(Integer planId, Integer userId, Integer receivingUserId, String fromto, String tipoPlan, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.PlanAudioDTO(m.planAudioId, m.name, m.creationDate, m.toUserId.userId, m.toStar, m.stateId) ");
        sql.append("FROM PlanAudio m ");
        if (fromto.equals("from")) {
            sql.append("Where m.fromUserId.userId = :userId ");
            sql.append("And m.toUserId.userId = :receiveUserId ");
        } else {
            sql.append("Where m.toUserId.userId = :userId ");
            sql.append("And m.fromUserId.userId = :receiveUserId ");
        }
        if (tipoPlan.equals("IN")) {
            sql.append("And m.coachAssignedPlanId.coachAssignedPlanId = :planId ");
        } else {
            sql.append("And m.coachExtAthleteId.coachExtAthleteId = :planId ");
        }
         if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" and  m.toStar = ").append(Boolean.FALSE);
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" and  m.toStar =  ").append(Boolean.TRUE);
        }
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("receiveUserId", receivingUserId);
        query.setParameter("planId", planId);
        return query.getResultList();
    }

    @Override
    public PlanAudio getAudioById(Integer id) throws DAOException {
        try {
            String qlString = "SELECT v FROM PlanAudio v WHERE v.planAudioId = :planAudioId ";
            setParameter("planAudioId", id);
            List<PlanAudio> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Integer getCountAudioByPlan(Integer coachAssignedPlanId, Integer fromUserId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.audio_count  - count(m.plan_audio_id)) > 0 THEN (cp.audio_count - count(m.plan_audio_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {

            sql.append(" And m.to_star = true ");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        if (roleSelected != -1) {
            sql.append(" And cp.communication_role_id = ").append(roleSelected);
        }

        sql.append(" Group by cp.audio_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountAudioByPlanExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.audio_count - count(m.plan_audio_id)) > 0 THEN (cp.audio_count  - count(m.plan_audio_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.audio_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountAudiosReceived(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_audio_id) ");
        sql.append(" FROM plan_audio m ");
        sql.append(" Where m.from_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
        if (roleSelected != -1 && roleSelected == RoleEnum.COACH_INTERNO.getId()) {
            sql.append(" And m.to_star = false ");
        } else if (roleSelected != -1 && roleSelected == RoleEnum.ESTRELLA.getId()) {

            sql.append(" And m.to_star = true ");
        }

        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_audio_id) ");
        sql.append(" FROM plan_audio m ");
        sql.append(" Where m.from_user_id = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public void readAudios(Integer coachAssignedPlanId, Integer userId, Integer roleSelected) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_audio ");
        builder.append(" set readed = true ");
        builder.append(" where  to_user_id = ").append(userId);
        builder.append(" and  coach_assigned_plan_id = ").append(coachAssignedPlanId);
        if (roleSelected != -1 && roleSelected == RoleEnum.COACH_INTERNO.getId()) {
            builder.append(" And to_star = false ");
        } else if (roleSelected != -1 && roleSelected == RoleEnum.ESTRELLA.getId()) {

            builder.append(" And to_star = true ");
        }
        executeNativeUpdate(builder.toString());
    }

    @Override
    public void readAudiosExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_audio ");
        builder.append(" set readed = true ");
        builder.append(" where  to_user_id = ").append(userId);
        builder.append(" and  coach_ext_athlete_id = ").append(planId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public void readAudio(Integer planAudioId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_audio ");
        builder.append(" set readed = true ");
        builder.append(" where  plan_audio_id = ").append(planAudioId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public int getCountAudioEmergencyByPlan(Integer planId, Integer fromUserId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.audio_count + cp.audio_emergency)  - count(m.plan_audio_id)) > 0 THEN ((cp.audio_count + cp.audio_emergency) - count(m.plan_audio_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = false ");
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {

            sql.append(" And m.to_star = true ");
        }
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        if (roleSelected != -1) {
            sql.append(" And cp.communication_role_id = ").append(roleSelected);
        }

        sql.append(" Group by cp.audio_count, cp.audio_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public int getCountAudioByEmergencyPlanExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.audio_count + cp.audio_emergency)  - count(m.plan_audio_id)) > 0 THEN ((cp.audio_count + cp.audio_emergency) - count(m.plan_audio_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.audio_count, cp.audio_emergency  ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

}
