/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.plan.TrainingUserSerieDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.TrainingUserSerie;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Edwin G
 */
@Repository
public class TrainingUserSerieDaoImpl extends BaseDAOImpl<TrainingUserSerie> implements TrainingUserSerieDao {

    private final int batchSize = 10;

    @Override
    public List<TrainingUserSerie> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId,");
        sql.append("t.workoutDate, t.activityId, t.manualActivityId,  u.userId.userId,");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = u.userId.userId), t.isDrag , t.executedTime, t.executedDistance, t.indStrava, t.lastUpdateStrava");
        sql.append(") FROM TrainingPlanWorkout t, TrainingPlanUser u ");
        sql.append("WHERE u.trainingPlanUserId = t.trainingPlanUserId.trainingPlanUserId ");
        sql.append("AND u.userId.userId = :userId ");
        sql.append("AND t.workoutDate BETWEEN :fromDate AND :toDate ");
        sql.append("AND u.stateId = :active ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        query.setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        List<TrainingUserSerie> list = query.getResultList();
        return list;
    }

    @Override
    public List<TrainingUserSerie> createList(List<TrainingUserSerie> list) throws Exception {
        List<TrainingUserSerie> listCreated = bulkSave(list);
        return listCreated;
    }

    @Override
    public TrainingUserSerie createTrainingUserSerie(TrainingUserSerie trainingPlanWorkout) throws Exception {
        return create(trainingPlanWorkout);
    }

    private <T extends TrainingUserSerie> List<T> bulkSave(List<T> entities) {
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

    private <T extends TrainingUserSerie> T persistOrMerge(T t) {
        if (t.getTrainingUserSerieId() == null) {
            getEntityManager().persist(t);
            return t;
        } else {
            getEntityManager().merge(t);
            return t;
        }
    }
}
