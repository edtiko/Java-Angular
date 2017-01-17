/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.PlanWorkoutObjective;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanWorkoutObjectiveDao extends BaseDAO<PlanWorkoutObjective>{
	


    public List<PlanWorkoutObjective> findAll() throws Exception;

    public List<PlanWorkoutObjective> findAllActive() throws Exception;

    public PlanWorkoutObjective findByTrainingPlanUserId(Integer trainingPlanUserId, Date fromDate, Date toDate) throws Exception;   

    public PlanWorkoutObjective findById(Integer e) throws Exception;   

    public List<PlanWorkoutObjective> findByFiltro(PlanWorkoutObjective filtro) throws Exception; 

    public PlanWorkoutObjective findCurrentObjective(Integer trainingPlanUserId) throws Exception; 

    public void inactivateOld(Integer trainingPlanUserId) throws Exception; 
}
