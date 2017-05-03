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
import co.expertic.training.model.dto.TrainingUserSerieDTO;
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
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception {
        StringBuilder sql = new StringBuilder();
        List<TrainingPlanWorkoutDto> list = new ArrayList<>();
        sql.append("SELECT new co.expertic.training.model.dto.TrainingPlanWorkoutDto(t.trainingPlanWorkoutId,");
        sql.append("t.workoutDate, t.manualActivityId,  u.userId.userId, t.isDrag ");
        sql.append(") FROM TrainingPlanWorkout t, TrainingPlanUser u ");
        sql.append("WHERE u.trainingPlanUserId = t.trainingPlanUserId.trainingPlanUserId ");
        sql.append("AND u.userId.userId = :userId ");
        sql.append("AND t.workoutDate BETWEEN :fromDate AND :toDate ");
        sql.append("AND t.manualActivityId is not null ");
        sql.append("AND u.stateId = :active ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        query.setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        List<TrainingPlanWorkoutDto> manualList = query.getResultList();

        StringBuilder sql2 = new StringBuilder();
        sql2.append("SELECT DISTINCT new co.expertic.training.model.dto.TrainingPlanWorkoutDto(t.trainingUserSerieId,");
        sql2.append("t.workDate, t.numSeries, t.serieTime, t.week, t.sesion, t.restTime, t.numZona, du.discipline ) ");
        sql2.append(" FROM TrainingUserSerie t, DisciplineUser du ");
        sql2.append("WHERE t.trainingPlanUserId.userId.userId = :userId ");
        sql2.append("AND du.userId.userId = :userId ");
        sql2.append("AND t.workDate BETWEEN :fromDate AND :toDate ");
        sql2.append(" ORDER BY t.numZona DESC ");
        query = getEntityManager().createQuery(sql2.toString());
        query.setParameter("userId", userId);
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        List<TrainingPlanWorkoutDto> serieList = query.getResultList();
        list.addAll(manualList);
        list.addAll(serieList);

        return list;
    }

    @Override
    public TrainingUserSerie getPlanWorkoutByUser(Integer userId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e ");
        sql.append("FROM TrainingUserSerie e ");
        sql.append("WHERE e.trainingPlanUserId.userId.userId = :userId ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        List<TrainingUserSerie> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
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

    @Override
    public void deleteSeriesByTrainingPlanUserId(Integer trainingPlanUserId) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append(" delete from training_user_serie ");
        builder.append(" where training_plan_user_id = ").append(trainingPlanUserId);
        executeNativeUpdate(builder.toString());
    }

    @Override
    public TrainingUserSerieDTO getPlanWorkoutById(Integer id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  new co.expertic.training.model.dto.TrainingUserSerieDTO(e) ");
        sql.append("FROM TrainingUserSerie e ");
        sql.append("WHERE e.trainingUserSerieId = :id ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("id", id);
        List<TrainingUserSerieDTO> list = query.getResultList();

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<TrainingUserSerieDTO> getSerieBySesionWeekUser(Integer userId, Integer sesion, Integer week) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT  new co.expertic.training.model.dto.TrainingUserSerieDTO(e) ");
        sql.append("FROM TrainingUserSerie e ");
        sql.append("WHERE e.trainingPlanUserId.userId.userId = :userId ");
        sql.append("AND e.sesion = :sesion ");
        sql.append("AND e.week = :week ");
        sql.append(" ORDER BY e.numZona DESC ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("userId", userId);
        query.setParameter("sesion", sesion);
        query.setParameter("week", week);
        List<TrainingUserSerieDTO> list = query.getResultList();


        return list;
    }
}
