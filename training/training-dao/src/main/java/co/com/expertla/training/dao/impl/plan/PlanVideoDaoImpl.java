/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.dao.plan.PlanVideoDao;
import co.com.expertla.training.model.dto.PlanVideoDTO;
import co.com.expertla.training.model.entities.PlanVideo;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanVideoDaoImpl extends BaseDAOImpl<PlanVideo> implements PlanVideoDao {

    @Override
    public PlanVideo getByVideoPath(String fileName) throws DAOException {
        try {
            String qlString = "SELECT v FROM PlanVideo v WHERE v.videoPath = :videoPath ";
            setParameter("videoPath", fileName);
            List<PlanVideo> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlanVideoDTO> getVideosByUser(Integer coachAssignedPlanId, Integer userId, String fromto) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.PlanVideoDTO(m.planVideoId, m.name, m.fromUserId, m.toUserId, m.creationDate) ");
        sql.append("FROM PlanVideo m ");
        if (fromto.equals("from")) {
            sql.append("Where m.fromUserId.userId = :userId ");
        } else {
            sql.append("Where m.toUserId.userId = :userId ");
        }
        sql.append("And m.coachAssignedPlanId.coachAssignedPlanId = :coachAssignedPlanId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("coachAssignedPlanId", coachAssignedPlanId);
        return query.getResultList();
    }

    @Override
    public PlanVideo getVideoById(Integer id) throws DAOException {
        try {
            String qlString = "SELECT v FROM PlanVideo v WHERE v.planVideoId = :planVideoId ";
            setParameter("planVideoId", id);
            List<PlanVideo> query = createQuery(qlString);
            return query.get(0);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (t.video_count  - count(m.plan_video_id)) >= 0 THEN (t.video_count  - count(m.plan_video_id)) ");
        sql.append(" ELSE t.video_count END ");
        sql.append(" FROM training_plan_user tu, training_plan t, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_video m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" Group by t.video_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer userId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_video_id) ");
        sql.append(" FROM plan_video m ");
        sql.append(" Where m.from_user_id = ").append(userId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }

    @Override
    public void readVideos(Integer coachAssignedPlanId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_video ");
        builder.append(" set readed = true ");
        builder.append(" where  to_user_id = ").append(userId);
        builder.append(" and  coach_assigned_plan_id = ").append(coachAssignedPlanId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public void readVideo(Integer planVideoId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_video ");
        builder.append(" set readed = true ");
        builder.append(" where  plan_video_id = ").append(planVideoId);
        executeNativeUpdate(builder.toString());
    }

}
