package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.PlanAudioDao;
import co.com.expertla.training.model.dto.PlanAudioDTO;
import co.com.expertla.training.model.entities.PlanAudio;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanAudioDaoImpl extends BaseDAOImpl<PlanAudio> implements PlanAudioDao {

    @Override
    public PlanAudio getByAudioPath(String fileName)throws DAOException {
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
    public List<PlanAudioDTO> getAudiosByUser(Integer planId, Integer userId, String fromto, String tipoPlan) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanAudioDTO(m.planAudioId, m.name, m.fromUserId, m.toUserId, m.creationDate) ");
        sql.append("FROM PlanAudio m ");
        if (fromto.equals("from")) {
            sql.append("Where m.fromUserId.userId = :userId ");
        } else {
            sql.append("Where m.toUserId.userId = :userId ");
        }
        if (tipoPlan.equals("IN")) {
            sql.append("And m.coachAssignedPlanId.coachAssignedPlanId = :planId ");
        } else {
            sql.append("And m.coachExtAthleteId.coachExtAthleteId = :planId ");
        }
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
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
    public Integer getCountAudioByPlan(Integer coachAssignedPlanId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (t.audio_count  - count(m.plan_audio_id)) > 0 THEN (t.audio_count - count(m.plan_audio_id)) ");
        sql.append(" ELSE (t.audio_emergency) END ");
        sql.append(" FROM training_plan_user tu, training_plan t, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" Group by t.audio_count, t.audio_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountAudioByPlanExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (t.audio_count - count(m.plan_audio_id)) > 0 THEN (t.audio_count  - count(m.plan_audio_id)) ");
        sql.append(" ELSE (t.audio_emergency) END ");
        sql.append(" FROM training_plan_user tu, training_plan t, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_audio m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" Group by t.audio_count, t.audio_emergency  ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountAudiosReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_audio_id) ");
        sql.append(" FROM plan_audio m ");
        sql.append(" Where m.to_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public Integer getCountAudiosReceivedExt(Integer planId, Integer userId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_audio_id) ");
        sql.append(" FROM plan_audio m ");
        sql.append(" Where m.to_user_id = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public void readAudios(Integer coachAssignedPlanId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_audio ");
        builder.append(" set readed = true ");
        builder.append(" where  to_user_id = ").append(userId);
        builder.append(" and  coach_assigned_plan_id = ").append(coachAssignedPlanId);
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

}