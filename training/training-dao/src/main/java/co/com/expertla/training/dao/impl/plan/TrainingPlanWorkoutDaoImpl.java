package co.com.expertla.training.dao.impl.plan;

import co.com.expertla.base.jpa.BaseDAOImpl;
import java.util.List;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.dao.plan.TrainingPlanWorkoutDao;
import co.com.expertla.training.enums.Status;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Repository;

/**
 * TrainingPlanWorkoutDaoImpl <br>
 * Info. Creación: <br>
 * fecha 15/07/2016 <br>
 *
 * @author Andres Felipe Lopez Rodriguez
*
 */
@Repository
public class TrainingPlanWorkoutDaoImpl extends BaseDAOImpl<TrainingPlanWorkout> implements TrainingPlanWorkoutDao {

    private final int batchSize = 10;
    
    @Override
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(User user, Date fromDate, Date toDate) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId,");
        sql.append("t.workoutDate, t.activityId, t.manualActivityId,  u.userId.userId,");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = u.userId.userId), t.isDrag ");
        sql.append(") FROM TrainingPlanWorkout t, TrainingPlanUser u ");
        sql.append("WHERE u.trainingPlanUserId = t.trainingPlanUserId.trainingPlanUserId ");
        sql.append("AND u.userId.userId = :userId ");
        sql.append("AND t.workoutDate BETWEEN :fromDate AND :toDate ");
        sql.append("AND u.stateId = :active ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", user.getUserId());
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        query.setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        List<TrainingPlanWorkoutDto> list = query.getResultList();
        return list;
    }

    @Override
    public List<TrainingPlanWorkout> createList (List<TrainingPlanWorkout> list) throws Exception {
        List<TrainingPlanWorkout> listCreated = bulkSave(list);
        return listCreated;
    }
    
    @Override
    public TrainingPlanWorkout createTrainingPlanWorkout (TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        return create(trainingPlanWorkout);
    }
    
    private <T extends TrainingPlanWorkout> List<T> bulkSave(List<T> entities) {
        final List<T> savedEntities = new ArrayList<>(entities.size());
        int i = 0;
        for (T t : entities) {
            savedEntities.add(persistOrMerge(t));
            i++;
            if (i % batchSize == 0) {
                // Flush a batch of inserts and release memory.
                this.getEntityManager().flush();
                this.getEntityManager().clear();
            }
        }
        return savedEntities;
    }
 
    private <T extends TrainingPlanWorkout> T persistOrMerge(T t) {
        if (t.getTrainingPlanWorkoutId()== null) {
            getEntityManager().persist(t);
            return t;
        } else {
            getEntityManager().merge(t);
            return t;
        }
    }

    @Override
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM TrainingPlanWorkout t ");
        sql.append("WHERE t.trainingPlanWorkoutId = :trainingPlanWorkoutId ");
        setParameter("trainingPlanWorkoutId", trainingPlanWorkout.getTrainingPlanWorkoutId());
        return createQuery(sql.toString());
    }

    @Override
    public void deleteByManualActivityId(Integer manualActivityId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(" delete from training_plan_workout ");
        builder.append(" where  manual_activity_id = ").append(manualActivityId);
        executeNativeUpdate(builder.toString());
    }
    
        
    @Override
    public TrainingPlanWorkoutDto getPlanWorkoutById(Integer trainingPlanWorkoutId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId, t.workoutDate, t.activityId, t.manualActivityId, t.trainingPlanUserId.userId.userId, ");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = t.trainingPlanUserId.userId.userId) )");
        sql.append("FROM TrainingPlanWorkout t ");
        sql.append("WHERE t.trainingPlanWorkoutId = :trainingPlanWorkoutId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingPlanWorkoutId", trainingPlanWorkoutId);
        List<TrainingPlanWorkoutDto> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }

    @Override
    public TrainingPlanWorkoutDto getPlanWorkoutByUser(Integer userId) throws Exception {
           StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.com.expertla.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId, t.workoutDate, t.activityId, t.manualActivityId, t.trainingPlanUserId.userId.userId, ");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = t.trainingPlanUserId.userId.userId) )");
        sql.append("FROM TrainingPlanWorkout t ");
        sql.append("WHERE t.trainingPlanUserId.userId.userId = :userId ");
        sql.append(" And t.trainingPlanUserId.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<TrainingPlanWorkoutDto> list = query.getResultList();
        
        if(list != null && !list.isEmpty()) {
            return list.get(0);
        }
        
        return null;
    }
}
