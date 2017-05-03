package co.expertic.training.service.plan;

import co.expertic.training.model.dto.PlanWorkoutDTO;
import co.expertic.training.model.dto.TrainingPlanWorkoutDto;
import co.expertic.training.model.dto.TrainingUserSerieDTO;
import co.expertic.training.model.entities.TrainingPlanWorkout;
import co.expertic.training.model.entities.TrainingUserSerie;
import co.expertic.training.model.entities.ZoneTimeSerie;
import java.util.Date;
import java.util.List;

/**
* TrainingPlanWorkoutService <br>
* Info. Creación: <br>
* fecha 15/07/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface TrainingPlanWorkoutService {
     
     /**
     * Obtiene el plan de entrenamiento por usuario <br>
     * Info. Creación: <br>
     * fecha 15/07/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userId
     * @param fromDate
     * @param toDate
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(Integer userId, Date fromDate, Date toDate) throws Exception;
    
    /**
     * Genera el plan de entrenamiento para el usuario
     * @param id
     * @param fromDate
     * @param toDate
     * @throws Exception 
     */
    public void generatePlan(Integer id,Date fromDate) throws Exception;
    
    
    /**
     * crea TrainingPlanWorkout <br>
     * Info. Creación: <br>
     * fecha 05/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param planWorkout
     * @return 
     * @throws Exception 
     */
    public TrainingPlanWorkout create(TrainingPlanWorkout planWorkout) throws Exception;
    
    /**
     * elimina TrainingPlanWorkout <br>
     * Info. Creación: <br>
     * fecha 08/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @throws Exception 
     */
    public void delete(TrainingPlanWorkout trainingPlanWorkout) throws Exception;
    
    /**
     * Obtiene el plan de entrenamiento por id <br>
     * Info. Creación: <br>
     * fecha 08/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanWorkout> getById(TrainingPlanWorkout trainingPlanWorkout) throws Exception;
    
      public TrainingUserSerieDTO getPlanWorkoutById(Integer id) throws Exception; 

    public TrainingUserSerie getPlanWorkoutByUser(Integer userId)throws Exception; 

    public void update(PlanWorkoutDTO planWorkoutDTO)throws Exception; 

    public void updateStrava(TrainingPlanWorkoutDto dto, Boolean isStrava)throws Exception; 

    public boolean isApproved(Integer trainingPlanUserId, Date fromDate, Date toDate, Integer objectiveId)throws Exception; 

    public List<TrainingUserSerieDTO> getSerieBySesionWeekUser(Integer userId, Integer sesion, Integer week)throws Exception; 

    public ZoneTimeSerie getZoneTimesByUser(Integer userId) throws Exception;
 
}