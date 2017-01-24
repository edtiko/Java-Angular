/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.impl.plan;

import co.expertic.base.jpa.BaseDAOImpl;
import co.expertic.training.dao.plan.PlanWorkoutObjectiveDao;
import co.expertic.training.model.entities.PlanWorkoutObjective;
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
public class PlanWorkoutObjectiveDaoImpl  extends BaseDAOImpl<PlanWorkoutObjective> implements PlanWorkoutObjectiveDao {

    @Override
    public List<PlanWorkoutObjective> findAll() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PlanWorkoutObjective> findAllActive() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanWorkoutObjective findByTrainingPlanUserId(Integer trainingPlanUserId, Date fromDate, Date toDate) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM PlanWorkoutObjective t ");
        sql.append("WHERE t.trainingPlanUserId.trainingPlanUserId = :trainingPlanUserId ");
        sql.append("AND t.fromDate = :fromDate ");
        sql.append("AND t.toDate   = :toDate ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingPlanUserId", trainingPlanUserId);
        query.setParameter("fromDate", fromDate, TemporalType.DATE);
        query.setParameter("toDate", toDate, TemporalType.DATE);
        //query.setParameter("active", Short.valueOf(Status.ACTIVE.getId()));
        List<PlanWorkoutObjective> list = query.getResultList();

        return list != null ? list.get(0) : null;
    }

    @Override
    public PlanWorkoutObjective findById(Integer e) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PlanWorkoutObjective> findByFiltro(PlanWorkoutObjective filtro) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PlanWorkoutObjective findCurrentObjective(Integer trainingPlanUserId) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT t FROM PlanWorkoutObjective t ");
        sql.append("WHERE t.trainingPlanUserId.trainingPlanUserId = :trainingPlanUserId ");
        sql.append("AND t.active = :active ");
        Query query = getEntityManager().createQuery(sql.toString());
        query.setParameter("trainingPlanUserId", trainingPlanUserId);
        query.setParameter("active", Boolean.TRUE);
        List<PlanWorkoutObjective> list = query.getResultList();

        return list != null ? list.get(0) : null;
    }

    @Override
    public void inactivateOld(Integer trainingPlanUserId) throws Exception {
       StringBuilder builder = new StringBuilder();
        builder.append("update plan_workout_objective ");
        builder.append("set active = false ");
        builder.append("where training_plan_user_id = ").append(trainingPlanUserId);
        executeNativeUpdate(builder.toString());
    }
    
}
