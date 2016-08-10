package co.com.expertla.training.plan.service;

import co.com.expertla.training.model.dto.TrainingPlanWorkoutDto;
import co.com.expertla.training.model.entities.TrainingPlanWorkout;
import co.com.expertla.training.model.entities.User;
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
     * @param user
     * @param fromDate
     * @param toDate
     * @return 
     * @throws Exception 
     */
    public List<TrainingPlanWorkoutDto> getPlanWorkoutByUser(User user, Date fromDate, Date toDate) throws Exception;
    
    
    public void generatePlan(Integer id,Date fromDate, Date toDate) throws Exception;
    
    
    /**
     * crea TrainingPlanWorkout <br>
     * Info. Creación: <br>
     * fecha 05/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param trainingPlanWorkout
     * @return 
     * @throws Exception 
     */
    public TrainingPlanWorkout create(TrainingPlanWorkout trainingPlanWorkout) throws Exception;
    
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

 
}