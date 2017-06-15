/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.dao.plan.PlanVideoDao;
import co.expertic.training.dao.user.UserDao;
import co.expertic.training.enums.RoleEnum;
import co.expertic.training.model.dto.PlanVideoDTO;
import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.PlanVideo;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class PlanVideoDaoImpl extends BaseDAOImpl<PlanVideo> implements PlanVideoDao {

    @Autowired
    private UserDao userDao;

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
    public List<PlanVideoDTO> getVideosByUser(Map param) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.PlanVideoDTO(m.planVideoId, m.name, ");
        sql.append("m.fromUserId.userId, m.toUserId.userId, m.creationDate, m.indRejected, m.fromPlanVideoId, m.readed) ");
        sql.append("FROM PlanVideo m ");
        if (param.get("fromto").equals("from")) {
            sql.append("Where m.fromUserId.userId = :userId ");
            sql.append("And m.toUserId.userId = :toUserId ");
        } else {
            sql.append("Where m.toUserId.userId = :userId ");
            sql.append("And m.fromUserId.userId = :toUserId ");
        }

        if ((int) param.get("planId") != -1) {
            if (param.get("tipoPlan").equals("IN")) {
                sql.append("And m.coachAssignedPlanId.coachAssignedPlanId = :planId ");
            } else {
                sql.append(" And m.coachExtAthleteId.coachExtAthleteId = :planId ");
            }
        } else {
            sql.append(" And m.coachAssignedPlanId = null ");
        }

          if ((int) param.get("roleSelected") != -1 && (int) param.get("roleSelected") == RoleEnum.COACH_INTERNO.getId()) {
            sql.append(" and  m.toStar = ").append(Boolean.FALSE);
        } else if ((int) param.get("roleSelected") != -1 && (int) param.get("roleSelected") == RoleEnum.ESTRELLA.getId()) {
            sql.append(" and  m.toStar =  ").append(Boolean.TRUE);
        }
          
          sql.append(" Order by m.creationDate desc ");

        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", (int) param.get("userId"));
        query.setParameter("toUserId", (int) param.get("toUserId"));
        if ((int) param.get("planId") != -1) {
            query.setParameter("planId", (int) param.get("planId"));
        }
        
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
    public Integer getCountVideoByPlan(Integer coachAssignedPlanId, Integer fromUserId,Integer toUserId, Integer roleSelected) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.video_count  - count(m.plan_video_id)) >= 0 THEN (cp.video_count  - count(m.plan_video_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_video m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.to_user_id =  ").append(toUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())){
            sql.append(" And m.to_star = ").append(Boolean.FALSE);
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())){
            sql.append(" And m.to_star = ").append(Boolean.TRUE);
        }
        
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(roleSelected);
        sql.append(" Group by cp.video_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountVideoByPlanExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN (cp.video_count  - count(m.plan_video_id)) > 0 THEN (cp.video_count  - count(m.plan_video_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_video m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.video_count ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountVideosReceived(Integer coachAssignedPlanId, Integer fromUserId, Integer toUserId, Integer roleSelected) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_video_id) ");
        sql.append(" FROM plan_video m ");
        sql.append(" Where m.from_user_id = ").append(fromUserId);
        sql.append(" And m.to_user_id = ").append(toUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(coachAssignedPlanId);
        sql.append(" And m.readed = false");
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = ").append(Boolean.FALSE);
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.to_star = ").append(Boolean.TRUE);
        }
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.get(0).intValue();
    }
    
    @Override
    public int getCountVideoEmergencyIn(Integer planId, Integer fromUserId, Integer toUserId, Integer roleSelected) throws DAOException {  
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.video_count + cp.video_emergency)  - count(m.plan_video_id)) > 0 THEN ((cp.video_count + cp.video_emergency) - count(m.plan_video_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_assigned_plan c ");
        sql.append(" LEFT JOIN plan_video m ON m.coach_assigned_plan_id = c.coach_assigned_plan_id");
        if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.COACH_INTERNO.getId())) {
            sql.append(" And m.to_star = ").append(Boolean.FALSE);
        } else if (roleSelected != -1 && Objects.equals(roleSelected, RoleEnum.ESTRELLA.getId())) {
            sql.append(" And m.to_star = ").append(Boolean.TRUE);
        }
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.to_user_id =  ").append(toUserId);
        sql.append(" And m.coach_assigned_plan_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_assigned_plan_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(roleSelected);
        sql.append(" Group by cp.video_count, cp.video_emergency ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public int getCountVideoEmergencyExt(Integer planId, Integer fromUserId) throws DAOException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CASE  ");
        sql.append(" WHEN ((cp.video_count + cp.video_emergency)  - count(m.plan_video_id)) > 0 THEN ((cp.video_count + cp.video_emergency) - count(m.plan_video_id)) ");
        sql.append(" ELSE 0 END ");
        sql.append(" FROM training_plan_user tu, training_plan t, configuration_plan cp, coach_ext_athlete c ");
        sql.append(" LEFT JOIN plan_video m ON m.coach_ext_athlete_id = c.coach_ext_athlete_id");
        sql.append(" And m.from_user_id = ").append(fromUserId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
        sql.append(" Where c.training_plan_user_id  = tu.training_plan_user_id  ");
        sql.append(" And c.coach_ext_athlete_id = ").append(planId);
        sql.append(" And tu.training_plan_id = t.training_plan_id ");
        sql.append(" And t.training_plan_id = cp.training_plan_id ");
        sql.append(" And cp.communication_role_id = ").append(RoleEnum.COACH.getId());
        sql.append(" Group by cp.video_count, cp.video_emergency  ");
        Query query = getEntityManager().createNativeQuery(sql.toString());

        List<Number> count = (List<Number>) query.getResultList();

        return count.size() > 0 ? count.get(0).intValue() : 0;
    }

    @Override
    public Integer getCountVideosReceivedExt(Integer planId, Integer userId) throws DAOException {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(m.plan_video_id) ");
        sql.append(" FROM plan_video m ");
        sql.append(" Where m.to_user_id = ").append(userId);
        sql.append(" And m.coach_ext_athlete_id = ").append(planId);
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
    public void readVideosExt(Integer planId, Integer userId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_video ");
        builder.append(" set readed = true ");
        builder.append(" where  to_user_id = ").append(userId);
        builder.append(" and  coach_ext_athlete_id = ").append(planId);
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
    
    @Override
    public void rejectedVideo(Integer planVideoId) throws DAOException {
        StringBuilder builder = new StringBuilder();
        builder.append(" update plan_video ");
        builder.append(" set ind_rejected = 1 ");
        builder.append(" where  plan_video_id = ").append(planVideoId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public List<PlanVideoDTO> getResponseTimeVideos(Integer userId, List<UserDTO> users) throws Exception {

        HashMap<Integer, UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(), user);
        }

        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date))  )as seconds ");
        builder.append("from plan_video  p where (p.from_user_id = ").append(userId).append(" or p.to_user_id = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from plan_video pp where pp.to_user_id = p.from_user_id and pp.from_user_id = p.to_user_id ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanVideoDTO> messageList = new ArrayList<>();
        PlanVideoDTO obj = new PlanVideoDTO();
        for (Object[] result : list) {
            obj = new PlanVideoDTO();
            obj.setFromUser(mapUsers.get((Integer) result[4]));
            obj.setToUser(mapUsers.get((Integer) result[5]));
            obj.setCreateDate((Date) result[6]);
            Double seconds = (Double) result[10];
            obj.setReadableTime(getTime(seconds, obj.getCreateDate()));
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
    public List<PlanVideo> getPlanVideoStarByCoach(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT p FROM PlanVideo p ");
        builder.append("WHERE p.toUserId.userId = :userId ");
        setParameter("userId", userId);
        return createQuery(builder.toString());
    }

    @Override
    public List<PlanVideo> getPlanVideoStarByStar(Integer userId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT p FROM PlanVideo p ");
        builder.append("WHERE p.fromUserId.userId = :userId ");
        setParameter("userId", userId);
        return createQuery(builder.toString());
    }

    @Override
    public List<PlanVideoDTO> getResponseCountVideo(Integer userId, List<UserDTO> users) throws Exception {
        HashMap<Integer, UserDTO> mapUsers = new HashMap<>();
        for (UserDTO user : users) {
            mapUsers.put(user.getUserId(), user);
        }
        UserDTO user = UserDTO.mapFromUserEntity(userDao.findById(userId));
        mapUsers.put(userId, user);
        StringBuilder builder = new StringBuilder();
        builder.append("select *, abs(extract(epoch from creation_date - lead(creation_date) over (order by creation_date)) / 3600 )as hours ");
        builder.append("from plan_video  p where (p.from_user_id = ").append(userId).append(" or p.to_user_id = ").append(userId).append(") ");
        builder.append("and exists (");
        builder.append("select 'x' from plan_video pp where pp.to_user_id = p.from_user_id and pp.from_user_id = p.to_user_id ");
        builder.append(")");
        Query query = this.getEntityManager().createNativeQuery(builder.toString());
        List<Object[]> list = query.getResultList();
        List<PlanVideoDTO> messageList = new ArrayList<>();
        PlanVideoDTO obj = new PlanVideoDTO();
        for (Object[] result : list) {
            obj = new PlanVideoDTO();
            obj.setFromUser(mapUsers.get((Integer) result[5]));
            obj.setToUser(mapUsers.get((Integer) result[5]));
            obj.setCreateDate((Date) result[6]);
            obj.setHours(result[10] == null ? getHours(obj.getCreateDate()) : (Double) result[10]);
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

}
