package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import java.util.List;

import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.dao.plan.TrainingPlanWorkoutDao;
import co.expertic.training.enums.Status;
import co.expertic.training.model.entities.IntensityZone;
import co.expertic.training.model.entities.IntensityZoneSesion;
import co.expertic.training.model.entities.MonthlyVolume;
import co.expertic.training.model.entities.WeeklyVolume;
import co.expertic.training.model.entities.ZoneTimeSerie;
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
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception {
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
        List<TrainingPlanWorkoutDto> list = query.getResultList();
        return list;
    }

    @Override
    public List<TrainingPlanWorkout> createList(List<TrainingPlanWorkout> list) throws Exception {
        List<TrainingPlanWorkout> listCreated = bulkSave(list);
        return listCreated;
    }

    @Override
    public TrainingPlanWorkout createTrainingPlanWorkout(TrainingPlanWorkout trainingPlanWorkout) throws Exception {
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
        if (t.getTrainingPlanWorkoutId() == null) {
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
        sql.append("SELECT new co.expertic.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId, t.workoutDate, t.activityId, t.manualActivityId, t.trainingPlanUserId.userId.userId, ");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = t.trainingPlanUserId.userId.userId), t.isDrag, t.executedTime, t.executedDistance, t.indStrava, t.lastUpdateStrava )");
        sql.append("FROM TrainingPlanWorkout t ");
        sql.append("WHERE t.trainingPlanWorkoutId = :trainingPlanWorkoutId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingPlanWorkoutId", trainingPlanWorkoutId);
        List<TrainingPlanWorkoutDto> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public TrainingPlanWorkoutDto getPlanWorkoutByUser(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId, t.workoutDate, t.activityId, t.manualActivityId, t.trainingPlanUserId.userId.userId, ");
        sql.append("(select up.weatherId.percentage FROM UserProfile up WHERE up.userId.userId = t.trainingPlanUserId.userId.userId), t.isDrag, t.executedTime, t.executedDistance, t.indStrava, t.lastUpdateStrava )");
        sql.append("FROM TrainingPlanWorkout t ");
        sql.append("WHERE t.trainingPlanUserId.userId.userId = :userId ");
        sql.append(" And t.trainingPlanUserId.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<TrainingPlanWorkoutDto> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public WeeklyVolume getWeeklyVolume(Integer trainingLevelId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM WeeklyVolume t ");
        sql.append("WHERE t.trainingLevelId.trainingLevelId = :trainingLevelId ");
        sql.append(" And t.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
         query.setParameter("trainingLevelId", trainingLevelId);
        List<WeeklyVolume> list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public MonthlyVolume getMonthlyVolume(Integer trainingLevelId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM MonthlyVolume t ");
        sql.append("WHERE t.trainingLevelId.trainingLevelId = :trainingLevelId ");
        sql.append(" And t.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingLevelId", trainingLevelId);
        List<MonthlyVolume> list = query.getResultList();
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public IntensityZone getIntensityZone(Integer trainingLevelId, Integer typeLoadId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM IntensityZone t ");
        sql.append("WHERE t.trainingLevelId.trainingLevelId = :trainingLevelId ");
        sql.append(" AND t.trainingLoadTypeId.trainingLoadTypeId = :typeLoadId ");
        sql.append(" And t.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingLevelId", trainingLevelId);
        query.setParameter("typeLoadId", typeLoadId);
        List<IntensityZone> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<IntensityZoneSesion> getIntensityZoneSesion(Integer numSesion, Integer trainingLevelId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM IntensityZoneSesion t ");
        sql.append("WHERE t.numSesion = :numSesion ");
        sql.append("AND   t.trainingLevelId.trainingLevelId = :trainingLevelId ");
        sql.append("AND t.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("numSesion", numSesion);
        query.setParameter("trainingLevelId", trainingLevelId);
        List<IntensityZoneSesion> list = query.getResultList();

        return list;
    }

    @Override
    public ZoneTimeSerie getZoneTimeSerie(Integer numZone) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t ");
        sql.append("FROM ZoneTimeSerie t ");
        sql.append("WHERE t.numZone = :numZone ");
        sql.append(" And t.stateId = ").append(Status.ACTIVE.getId());
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("numZone", numZone);
        List<ZoneTimeSerie> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
