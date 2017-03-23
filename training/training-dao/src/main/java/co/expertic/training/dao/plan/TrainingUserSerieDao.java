/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.entities.TrainingUserSerie;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface TrainingUserSerieDao extends BaseDAO<TrainingUserSerie> {
    
  public List<TrainingUserSerie> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception;
    
  public List<TrainingUserSerie> createList(List<TrainingUserSerie> list) throws Exception;
  
  public TrainingUserSerie createTrainingUserSerie(TrainingUserSerie trainingPlanWorkout) throws Exception;
    
}
