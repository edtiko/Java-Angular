package co.com.expertla.training.plan.dao.impl;

import co.com.expertla.base.jpa.BaseDAOImpl;
import java.util.List;

import co.com.expertla.training.model.entities.User;
import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.plan.dao.TrainingPlanWorkoutDao;
import java.util.ArrayList;
import java.util.Date;
import javax.persistence.EntityManager;
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
        sql.append("SELECT new co.com.expertla.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId, t.workoutDate, ");
        sql.append("t.activityId.activityId, t.activityId.name, t.activityId.modalityId.modalityId, t.activityId.modalityId.name, ");
        sql.append("t.activityId.objetiveId.objetiveId, t.activityId.objetiveId.name, ");
        sql.append("t.activityId.modalityId.disciplineId.disciplineId, t.activityId.modalityId.disciplineId.name, ");
        sql.append("t.activityId.objetiveId.level, u.userId.userId) ");
        sql.append("FROM TrainingPlanWorkout t, TrainingPlanUser u ");
        sql.append("WHERE u.trainingPlanId.trainingPlanId = t.trainingPlanId.trainingPlanId ");
        sql.append("AND u.userId.userId = :userId ");
        sql.append("AND t.workoutDate BETWEEN :fromDate AND :toDate ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", user.getUserId());
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        List<TrainingPlanWorkoutDto> list = query.getResultList();
        return list;
    }

    @Override
    public List<TrainingPlanWorkout> createList (List<TrainingPlanWorkout> list) throws Exception {
        List<TrainingPlanWorkout> listCreated = bulkSave(list);
        return listCreated;
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
}
