/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.plan;

import co.expertic.training.model.entities.PlanWorkoutObjective;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface PlanWorkoutObjectiveService {
    
     public List<PlanWorkoutObjective> findAll() throws Exception;

    public List<PlanWorkoutObjective> findAllActive() throws Exception;

    public PlanWorkoutObjective findByTrainingPlanUserId(Integer trainingPlanUserId, Date fromDate, Date toDate) throws Exception;   

    public PlanWorkoutObjective findById(Integer e) throws Exception;   

    public List<PlanWorkoutObjective> findByFiltro(PlanWorkoutObjective filtro) throws Exception; 

    public PlanWorkoutObjective findCurrentObjective(Integer trainingPlanUserId) throws Exception; 
}
