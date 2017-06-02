/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.plan;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.dto.TrainingUserSerieDTO;
import co.expertic.training.model.entities.TrainingUserSerie;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface TrainingUserSerieDao extends BaseDAO<TrainingUserSerie> {
    
  public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception;
  
  public Integer getCountPlanWorkoutByUser(Integer userId) throws Exception;
    
  public List<TrainingUserSerie> createList(List<TrainingUserSerie> list) throws Exception;
  
  public TrainingUserSerie createTrainingUserSerie(TrainingUserSerie trainingPlanWorkout) throws Exception;
  
  public TrainingUserSerie getPlanWorkoutByUser(Integer userId) throws Exception;
  
  public TrainingUserSerieDTO getPlanWorkoutById(Integer id) throws Exception;

  public void deleteSeriesByTrainingPlanUserId(Integer trainingPlanUserId) throws Exception;

    public List<TrainingUserSerieDTO> getSerieBySesionWeekUser(Integer userId, Integer sesion, Integer week)throws Exception;
    
}
