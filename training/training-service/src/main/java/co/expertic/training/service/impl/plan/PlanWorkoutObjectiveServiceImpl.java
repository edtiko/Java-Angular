/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.plan;

import co.expertic.training.dao.plan.PlanWorkoutObjectiveDao;
import co.expertic.training.model.entities.PlanWorkoutObjective;
import co.expertic.training.service.plan.PlanWorkoutObjectiveService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class PlanWorkoutObjectiveServiceImpl implements PlanWorkoutObjectiveService{
    
    @Autowired
    PlanWorkoutObjectiveDao planWorkoutObDao;

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
        return planWorkoutObDao.findByTrainingPlanUserId(trainingPlanUserId, fromDate, toDate);
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
       return planWorkoutObDao.findCurrentObjective(trainingPlanUserId);
    }
    
}
